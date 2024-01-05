package com.api.fleetmanagement.controller;

import com.api.fleetmanagement.models.TaxisModel;
import com.api.fleetmanagement.models.TrajectoriesModel;
import com.api.fleetmanagement.repositories.TaxisRepository;
import com.api.fleetmanagement.repositories.TrajectoriesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaxisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxisRepository taxisRepository;
    @MockBean
    private TrajectoriesRepository trajectoriesRepository;
    @Test
    void getAllTaxis() throws Exception {
        TaxisModel taxis = new TaxisModel();
        taxis.setPlate("CNCJ-2997");
        taxis.setId(7249);

        Page<TaxisModel> page = new PageImpl<>(List.of(taxis));

        Mockito.when(taxisRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        this.mockMvc.perform(get("/taxis"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'content':[{'id':7249,'plate':'CNCJ-2997'}],'pageable':'INSTANCE','last':true,'totalPages':1,'totalElements':1,'first':true,'size':1,'number':0,'sort':{'sorted':false,'empty':true,'unsorted':true},'numberOfElements':1,'empty':false}"));
    }

    @Test
    void getTaxiById_ExistingTaxi() throws Exception {
        TaxisModel taxi = new TaxisModel();
        taxi.setId(7249);
        taxi.setPlate("CNCJ-2997");

        TrajectoriesModel trajectory = new TrajectoriesModel();
        trajectory.setId(1);
        trajectory.setTaxi(taxi);
        trajectory.setDate(LocalDateTime.parse("2008-02-02T13:40:08"));
        trajectory.setLatitude(116.29118);
        trajectory.setLongitude(39.88652);

        Page<TrajectoriesModel> page = new PageImpl<>(List.of(trajectory));

        Mockito.when(taxisRepository.findById(7249)).thenReturn(Optional.of(taxi));
        Mockito.when(trajectoriesRepository.findTrajectoriesByTaxiId(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        this.mockMvc.perform(get("/taxis/7249"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].taxi.id").value(7249))
                .andExpect(jsonPath("$[0].taxi.plate").value("CNCJ-2997"))
                .andExpect(jsonPath("$[0].date").value("2008-02-02T13:40:08"))
                .andExpect(jsonPath("$[0].latitude").value(116.29118))
                .andExpect(jsonPath("$[0].longitude").value(39.88652));
    }

    @Test
    void getTaxiById_NonExistingTaxi() throws Exception {
        Mockito.when(taxisRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/taxis/7249"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}