package org.nft.builder.managers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_= {@Autowired})
public class ContextManager implements ApplicationContextAware {

    private ApplicationContext context;

    public Object getBean(String name) {
        return context.getBean(name);
    }

    public void addBean(Object bean, String name) {
        AutowireCapableBeanFactory bf = context.getAutowireCapableBeanFactory();
        bf.initializeBean(bean, name);
    }

    public void removeBean(String name) {
        AutowireCapableBeanFactory bf = context.getAutowireCapableBeanFactory();
        Object bean = bf.getBean(name);
        bf.destroyBean(bean);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
