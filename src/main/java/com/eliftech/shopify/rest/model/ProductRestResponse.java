package com.eliftech.shopify.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    private List<Variant> variants = null;

    private List<Option> options = null;

    private List<Image> images = null;

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
}
