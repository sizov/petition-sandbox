package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Subscriptions;
import com.mycompany.myapp.repository.SubscriptionsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SubscriptionsResource REST controller.
 *
 * @see SubscriptionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubscriptionsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_SUBSCRIBTION_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SUBSCRIBTION_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SUBSCRIBTION_DATE_TIME_STR = dateTimeFormatter.format(DEFAULT_SUBSCRIBTION_DATE_TIME);
    private static final String DEFAULT_SUBSCRIBER = "AAAAA";
    private static final String UPDATED_SUBSCRIBER = "BBBBB";

    @Inject
    private SubscriptionsRepository subscriptionsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubscriptionsMockMvc;

    private Subscriptions subscriptions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubscriptionsResource subscriptionsResource = new SubscriptionsResource();
        ReflectionTestUtils.setField(subscriptionsResource, "subscriptionsRepository", subscriptionsRepository);
        this.restSubscriptionsMockMvc = MockMvcBuilders.standaloneSetup(subscriptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subscriptions = new Subscriptions();
        subscriptions.setSubscribtionDateTime(DEFAULT_SUBSCRIBTION_DATE_TIME);
        subscriptions.setSubscriber(DEFAULT_SUBSCRIBER);
    }

    @Test
    @Transactional
    public void createSubscriptions() throws Exception {
        int databaseSizeBeforeCreate = subscriptionsRepository.findAll().size();

        // Create the Subscriptions

        restSubscriptionsMockMvc.perform(post("/api/subscriptionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subscriptions)))
                .andExpect(status().isCreated());

        // Validate the Subscriptions in the database
        List<Subscriptions> subscriptionss = subscriptionsRepository.findAll();
        assertThat(subscriptionss).hasSize(databaseSizeBeforeCreate + 1);
        Subscriptions testSubscriptions = subscriptionss.get(subscriptionss.size() - 1);
        assertThat(testSubscriptions.getSubscribtionDateTime()).isEqualTo(DEFAULT_SUBSCRIBTION_DATE_TIME);
        assertThat(testSubscriptions.getSubscriber()).isEqualTo(DEFAULT_SUBSCRIBER);
    }

    @Test
    @Transactional
    public void getAllSubscriptionss() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionss
        restSubscriptionsMockMvc.perform(get("/api/subscriptionss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptions.getId().intValue())))
                .andExpect(jsonPath("$.[*].subscribtionDateTime").value(hasItem(DEFAULT_SUBSCRIBTION_DATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].subscriber").value(hasItem(DEFAULT_SUBSCRIBER.toString())));
    }

    @Test
    @Transactional
    public void getSubscriptions() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get the subscriptions
        restSubscriptionsMockMvc.perform(get("/api/subscriptionss/{id}", subscriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subscriptions.getId().intValue()))
            .andExpect(jsonPath("$.subscribtionDateTime").value(DEFAULT_SUBSCRIBTION_DATE_TIME_STR))
            .andExpect(jsonPath("$.subscriber").value(DEFAULT_SUBSCRIBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptions() throws Exception {
        // Get the subscriptions
        restSubscriptionsMockMvc.perform(get("/api/subscriptionss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptions() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

		int databaseSizeBeforeUpdate = subscriptionsRepository.findAll().size();

        // Update the subscriptions
        subscriptions.setSubscribtionDateTime(UPDATED_SUBSCRIBTION_DATE_TIME);
        subscriptions.setSubscriber(UPDATED_SUBSCRIBER);

        restSubscriptionsMockMvc.perform(put("/api/subscriptionss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subscriptions)))
                .andExpect(status().isOk());

        // Validate the Subscriptions in the database
        List<Subscriptions> subscriptionss = subscriptionsRepository.findAll();
        assertThat(subscriptionss).hasSize(databaseSizeBeforeUpdate);
        Subscriptions testSubscriptions = subscriptionss.get(subscriptionss.size() - 1);
        assertThat(testSubscriptions.getSubscribtionDateTime()).isEqualTo(UPDATED_SUBSCRIBTION_DATE_TIME);
        assertThat(testSubscriptions.getSubscriber()).isEqualTo(UPDATED_SUBSCRIBER);
    }

    @Test
    @Transactional
    public void deleteSubscriptions() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

		int databaseSizeBeforeDelete = subscriptionsRepository.findAll().size();

        // Get the subscriptions
        restSubscriptionsMockMvc.perform(delete("/api/subscriptionss/{id}", subscriptions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Subscriptions> subscriptionss = subscriptionsRepository.findAll();
        assertThat(subscriptionss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
