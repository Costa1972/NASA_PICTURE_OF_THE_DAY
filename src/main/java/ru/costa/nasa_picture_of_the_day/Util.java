package ru.costa.nasa_picture_of_the_day;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.costa.nasa_picture_of_the_day.service.NasaService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


@Component
public class Util {
    private static final String URL = "https://api.nasa.gov/planetary/apod?api_key=K4WzXwpgu7KcXA8MNsq2q35K2KODztG4J73ZZkNR";
    private static final String FILE_PATH = "C:\\Users\\costa\\Desktop\\NASA\\";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final NasaService nasaService;

    @Autowired
    public Util(NasaService nasaService) {
        this.nasaService = nasaService;
    }

    @Bean
    private CloseableHttpClient httpClient() {
        return HttpClientBuilder
                .create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }

    public CloseableHttpResponse httpResponse() throws IOException {
        return httpClient().execute(new HttpGet(URL));
    }

    public void printNasaInfo() throws IOException {
        System.out.println(mappedNASA());
    }

    public NASA mappedNASA() throws IOException {
        try (CloseableHttpResponse response = httpResponse()){
            return MAPPER.readValue(response.getEntity().getContent(), NASA.class);
        }
    }

    @Bean
    public void postNasaToDatabase() throws IOException {
        String title = mappedNASA().getTitle();
        if (!nasaService.existsByTitle(title)) {
            nasaService.save(mappedNASA());
        }
    }

    public String getFileNameFromUrl() throws IOException {
        return Arrays
                .stream(mappedNASA().getHdurl()
                        .split("/"))
                .reduce((a, b) -> b)
                .orElse(null);
    }

    public HttpEntity getEntity() throws IOException {
        return httpClient().execute(new HttpGet(mappedNASA().getHdurl())).getEntity();
    }


    @Bean
    public void savePicture() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH + getFileNameFromUrl())) {
            getEntity().writeTo(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public void writeFromDBToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\costa\\Desktop\\NASA\\log.txt", false)) {
            List<NASA> nasaList = nasaService.get();
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            new FileWriter("C:\\Users\\costa\\Desktop\\NASA\\log.txt", false).close();
            nasaList.forEach(nasa -> printWriter.println(nasa.getId() + " " +
                    nasa.getDate() + " " + nasa.getExplanation() + " " + nasa.getHdurl() +
                    " " + nasa.getMedia_type() + " " + nasa.getService_version() + " " +
                    nasa.getTitle() + " " + nasa.getUrl() + " " + nasa.getCopyright()));

            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
