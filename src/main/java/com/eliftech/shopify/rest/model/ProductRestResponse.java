package com.eliftech.shopify.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRestResponse implements Serializable {

    private String id;

    private String title;

    private String bodyHtml;

    private String vendor;

    private String productType;

    private String createdAt;

    private String handle;

    private String updatedAt;

    private String publishedAt;

    private Object templateSuffix;

    private String publishedScope;

    private String tags;

    private String adminGraphqlApiId;

    private List<Variant> variants = Collections.emptyList();

    private List<Option> options = Collections.emptyList();

    private List<Image> images = Collections.emptyList();

    private Image image;

    private String status;

    private int colorIndex;

    private int sizeIndex;

    private String handleFixed;

    public String getHandleFixed() {
        return handle.toLowerCase();
    }

    public int getColorIndex() {
        List<Option> options = this.getOptions() == null ? Collections.emptyList() : this.getOptions();

        Optional<Option> color = options.stream()
                .filter(o -> StringUtils.containsIgnoreCase(o.getName(), "color") || StringUtils.containsIgnoreCase(o.getName(), "Colour"))
                .findFirst();

        if (color.isPresent() && color.get().getPosition() != null) {
            this.colorIndex = Integer.parseInt(color.get().getPosition());
        }
        return this.colorIndex;
    }

    public int getSizeIndex() {
        List<Option> options = this.getOptions() == null ? Collections.emptyList() : this.getOptions();

        Optional<Option> sizeOptional = options.stream()
                .filter(o -> StringUtils.containsIgnoreCase(o.getName(), "size"))
                .findFirst();

        if (sizeOptional.isPresent() && sizeOptional.get().getPosition() != null) {
            this.sizeIndex = Integer.parseInt(sizeOptional.get().getPosition());
        }
        return this.sizeIndex;
    }

    public static String formatNextBatchLink(String link) {
        return link != null? link.substring(link.indexOf('<') + 1, link.indexOf('>')) : "";
    }

    public static String parseNextBatchLinkAndFormat(String link) {

        if (link.indexOf("next") > 0) {

            int indexStart = link.indexOf(",");

            return link.substring(indexStart + 2, link.lastIndexOf(">") + 1);
        }

        return null;
    }
}
