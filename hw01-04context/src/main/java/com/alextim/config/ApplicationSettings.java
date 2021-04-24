package com.alextim.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
@Data
public class ApplicationSettings {

    private boolean useEnglishLocal;

    private int questionCount;
}
