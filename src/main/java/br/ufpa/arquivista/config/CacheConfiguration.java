package br.ufpa.arquivista.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(br.ufpa.arquivista.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Login.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Arquivista.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Arquivista.class.getName() + ".locadors", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Arquivista.class.getName() + ".locatarios", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Arquivista.class.getName() + ".imovels", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Arquivista.class.getName() + ".pagamentos", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Arquivista.class.getName() + ".comprovantes", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Arquivista.class.getName() + ".vistorias", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Locador.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Locador.class.getName() + ".pagamentos", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Locador.class.getName() + ".arquivistas", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Locatario.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Locatario.class.getName() + ".arquivistas", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Imovel.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Imovel.class.getName() + ".arquivistas", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Pagamento.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Pagamento.class.getName() + ".arquivistas", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Comprovante.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Comprovante.class.getName() + ".arquivistas", jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Vistoria.class.getName(), jcacheConfiguration);
            cm.createCache(br.ufpa.arquivista.domain.Vistoria.class.getName() + ".arquivistas", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
