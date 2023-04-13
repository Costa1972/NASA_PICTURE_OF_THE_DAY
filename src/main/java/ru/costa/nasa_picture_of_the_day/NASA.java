package ru.costa.nasa_picture_of_the_day;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "explanations")
@NoArgsConstructor
@Getter
public class NASA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "copyright", length = 2048)

    private String copyright;
    @Column(name = "date", length = 2048)
    private String date;
    @Column(name = "explanation", length = 2048)
    private String explanation;
    @Column(name = "hdurl", length = 2048)
    private String hdurl;
    @Column(name = "media_type", length = 2048)
    private String media_type;
    @Column(name = "service_version", length = 2048)
    private String service_version;
    @Column(name = "title", length = 2048)
    private String title;
    @Column(name = "url", length = 2048)
    private String url;

    public void setId(Long id) {
        this.id = id;
    }

    public NASA(@JsonProperty("copyright") String copyright,
                @JsonProperty("date") String date,
                @JsonProperty("explanation") String explanation,
                @JsonProperty("hdurl") String hdurl,
                @JsonProperty("media_type") String media_type,
                @JsonProperty("service_version") String service_version,
                @JsonProperty("title") String title,
                @JsonProperty("url") String url) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return "NASA\n{" +
                "\ncopyright='" + copyright + '\'' +
                ", \ndate='" + date + '\'' +
                ", \nexplanation='" + explanation + '\'' +
                ", \nhdurl='" + hdurl + '\'' +
                ", \nmedia_type='" + media_type + '\'' +
                ", \nservice_version='" + service_version + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \nurl='" + url + '\'' +
                "\n}";
    }
}
