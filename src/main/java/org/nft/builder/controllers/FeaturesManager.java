package org.nft.builder.controllers;

import lombok.*;
import org.nft.builder.gui.Menu;
import org.nft.builder.managers.ContextManager;
import org.nft.builder.models.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Hashtable;

@NoArgsConstructor(force = true)
@Getter
@Setter
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class FeaturesManager {

    @Setter(AccessLevel.NONE)
    private final Hashtable<String, Feature> managedFeatures = new Hashtable<>();

    private static final Hashtable<String, Feature> features = new Hashtable<>();

    @NonNull
    private final ContextManager contextManager;

    @PostConstruct
    private void init() {
        checkForFeatures();
    }

    public void checkForFeatures() {
        contextManager.getBeans(Feature.class).stream().parallel().forEach(obj -> {
            Feature feat = (Feature)obj;
            features.put(feat.getName(), feat);
        });
    }


    public void addManagedFeature(Feature feat) {
        if (feat == null) return;
        managedFeatures.put(feat.getName(), feat);
        features.put(feat.getName(), feat);
    }

    public void removeManagedFeature(String name) {
        Feature removed = managedFeatures.remove(name);
        if (removed == null) return;
        Menu menu = (Menu) contextManager.getBean("menu");
        menu.unregisterFeature(removed);
    }

}
