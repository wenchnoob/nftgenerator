package org.nft.builder.controllers;

import lombok.*;
import org.nft.builder.models.Feature;

import java.util.Collection;
import java.util.Hashtable;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Getter
@Setter
public class FeaturesManager {

    @NonNull
    @Setter(AccessLevel.NONE)
    private final Hashtable<String, Feature> managedFeatures;

    private static final Hashtable<String, Feature> features = new Hashtable<>();


    public void addManagedFeature(Feature feat) {
        if (feat == null) return;
        features.put(feat.getName(), feat);
    }

    public Feature getFeatureByName(String name) {
        return managedFeatures.get(name);
    }

    public Collection<Feature> getAllManagedFeatures() {
        return managedFeatures.values();
    }

    public Collection<Feature> getAllFeatures() {
        return features.values();
    }

}
