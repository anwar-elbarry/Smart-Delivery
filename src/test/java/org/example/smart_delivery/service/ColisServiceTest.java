package org.example.smart_delivery.service;

import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.dto.request.HistoriqueLivraisonDTO;
import org.example.smart_delivery.dto.response.ColisRespDTO;
import org.example.smart_delivery.entity.Colis;
import org.example.smart_delivery.entity.HistoriqueLivraison;
import org.example.smart_delivery.entity.Livreur;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.request.ColisMapper;
import org.example.smart_delivery.mapper.request.ColisProduitMapper;
import org.example.smart_delivery.mapper.request.HistoLivrMapper;
import org.example.smart_delivery.mapper.response.ColisRespMapper;
import org.example.smart_delivery.repository.*;
import org.example.smart_delivery.service.colis.ColisServiceImpl;
import org.example.smart_delivery.service.colis.Colisfilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Colis Servoice Test")
public class ColisServiceTest {

    @Mock
    private  ColisRepository colisRepository;
    @Mock
    private  ColisMapper colisMapper;
    @Mock
    private  LivreurRepository livreurRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  ProduitRepository produitRepository;
    @Mock
    private  ColisProduitRepository colisProduitRepository;
    @Mock
    private  ColisProduitMapper colisProduitMapper;
    @Mock
    private  ColisRespMapper colisRespMapper;
    @Mock
    private  HistoLivrMapper histoLivrMapper;
    @Mock
    private  ZoneRepository zoneRepository;
    @Mock
    private  HistoriqueLivraisonRepository historiqueLivraisonRepository;

    @InjectMocks
    private ColisServiceImpl colisService;


    private Colis colisEntity;
    private Colis savedColis;
    private ColisDTO colisDTO;
    private ColisRespDTO expectedResponse;
    private HistoriqueLivraison histoEntity;

    @BeforeEach
    void setup(){
            this.colisDTO = ColisDTO.builder()
                    .description("description")
                    .priorite(Priority.MEDIUM)
                    .poids(BigDecimal.valueOf(2.5))
                    .build();

        this.colisEntity = Colis.builder()
                .description("Test package")
                .poids(BigDecimal.valueOf(2.5))
                .build();


        this.savedColis = Colis.builder()
                .id("colis-uuid-123")
                .description("Test package")
                .poids(BigDecimal.valueOf(2.5))
                .priorite(Priority.MEDIUM)
                .statut(ColisStatus.CREATED)
                .build();


        this.expectedResponse = ColisRespDTO.builder()
                  .id("colis-uuid-123")
                  .description("Test package")
                  .poids(BigDecimal.valueOf(2.5))
                  .priorite(Priority.MEDIUM)
                  .statut(ColisStatus.CREATED)
                  .build();


        this.histoEntity = HistoriqueLivraison.builder()
                 .statut(ColisStatus.CREATED)
                 .commentaire("create colis")
                 .build();

    }

    @Test
    @DisplayName("Should create colis successfully with CREATED status and save historique")
    void testCreate_ShouldCreateColisSeccessfully(){

        // Mock behavior
        when(colisMapper.toEntity(colisDTO)).thenReturn(colisEntity);
        when(colisRepository.save(any(Colis.class))).thenReturn(savedColis);
        when(histoLivrMapper.toEntity(any(HistoriqueLivraisonDTO.class))).thenReturn(histoEntity);
        when(historiqueLivraisonRepository.save(any(HistoriqueLivraison.class))).thenReturn(histoEntity);
        when(colisRespMapper.toRespDto(savedColis)).thenReturn(expectedResponse);

        // When - Execute the method
        ColisRespDTO result = colisService.create(colisDTO);

        // Then - Verify results
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("colis-uuid-123");
        assertThat(result.getStatut()).isEqualTo(ColisStatus.CREATED);
        assertThat(result.getDescription()).isEqualTo("Test package");
        assertThat(result.getPoids()).isEqualByComparingTo(BigDecimal.valueOf(2.5));


        // Verify mapper was called with the DTO
        verify(colisMapper, times(1)).toEntity(colisDTO);

        // Verify entity was saved with correct status
        ArgumentCaptor<Colis> colisCaptor = ArgumentCaptor.forClass(Colis.class);
        verify(colisRepository, times(1)).save(colisCaptor.capture());
        Colis capturedColis = colisCaptor.getValue();
        assertThat(capturedColis.getStatut()).isEqualTo(ColisStatus.CREATED);
        assertThat(capturedColis.getLivreur()).isNull();

        // Verify historique was created and saved
        ArgumentCaptor<HistoriqueLivraisonDTO> histoCaptor = ArgumentCaptor.forClass(HistoriqueLivraisonDTO.class);
        verify(histoLivrMapper, times(1)).toEntity(histoCaptor.capture());
        HistoriqueLivraisonDTO capturedHisto = histoCaptor.getValue();
        assertThat(capturedHisto.getColisId()).isEqualTo("colis-uuid-123");
        assertThat(capturedHisto.getStatut()).isEqualTo(ColisStatus.CREATED);
        assertThat(capturedHisto.getCommentaire()).isEqualTo("create colis");
        assertThat(capturedHisto.getDateChangement()).isNotNull();

        verify(historiqueLivraisonRepository, times(1)).save(any(HistoriqueLivraison.class));
        verify(colisRespMapper, times(1)).toRespDto(savedColis);
    }


    @Test
    @DisplayName("test Update colis with success")
    void testUpdateColis_WithSuccess(){

        // mock
        when(colisMapper.toEntity(colisDTO)).thenReturn(colisEntity);
        when(colisRespMapper.toRespDto(savedColis)).thenReturn(expectedResponse);
        when(colisRepository.save(any(Colis.class))).thenReturn(savedColis);
        when(colisRepository.existsById("colis-uuid-123")).thenReturn(true);

        ColisRespDTO result = colisService.update("colis-uuid-123",colisDTO);

        assertThat(result.getId()).isEqualTo("colis-uuid-123");

        verify(colisRepository).save(any(Colis.class));
        verify(colisRepository).existsById("colis-uuid-123");

    }

    @Test
    @DisplayName("Find Exist Id With Error")
    void testCheckIfColisExist_WithError(){

        assertThatThrownBy(() -> colisService.checkIfColisExist("colis-uuid-123"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Colis not found with id: colis-uuid-123");
    }

    @Test
    @DisplayName("Get Colis By Id With Success")
    void testGetColisById_WithSuccess(){
        when(colisRepository.findById("colis-uuid-123")).thenReturn(Optional.of(savedColis));
        when(colisRespMapper.toRespDto(savedColis)).thenReturn(expectedResponse);

        ColisRespDTO result = colisService.getById("colis-uuid-123");

        assertThat(result.getId()).isEqualTo("colis-uuid-123");
        verify(colisRepository).findById(anyString());
    }

    @Test
    @DisplayName("Get All Colis By  With Success")
    void testGetAllColis_WithSucess(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").ascending());
        Page<Colis> pageEntity = new PageImpl<>(List.of(savedColis),pageable,1);

        when(colisRepository.findAll(pageable)).thenReturn(pageEntity);
        when(colisRespMapper.toRespDto(savedColis)).thenReturn(expectedResponse);

        Page<ColisRespDTO> result = colisService.getAll(pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(List.of(expectedResponse));

        verify(colisRepository).findAll(pageable);
        verify(colisRespMapper).toRespDto(savedColis);
    }

    @Test
    @DisplayName("Delete Colis By Id With Success")
    void testDeleteColis_WithSuccess(){
        when(colisRepository.findById("colis-uuid-123")).thenReturn(Optional.of(colisEntity));

        colisService.delete("colis-uuid-123");

        verify(colisRepository).findById("colis-uuid-123");
        verify(colisRepository).deleteById(anyString());
    }

    @Test
    @DisplayName("Assign colis to livreur with success")
    void testAssignColis_WithSuccess(){

        Livreur livreur = Livreur.builder()
                .id("livreur_123")
                .build();


        when(colisRepository.findById("colis-uuid-123")).thenReturn(Optional.of(colisEntity));
        when(livreurRepository.findById("livreur_123")).thenReturn(Optional.of(livreur));

        colisService.Assign_col("colis-uuid-123","livreur_123");

        assertThat(colisEntity.getStatut()).isEqualTo(ColisStatus.COLLECTED);
        assertThat(colisEntity.getLivreur()).isEqualTo(livreur);

        verify(colisRepository).save(any(Colis.class));
    }

    @Test
    @DisplayName("filter colis By priority with success")
    void testFilterByPriorite_WithSuccess(){
        Colisfilter colisfilter =  Colisfilter.builder()
                .priority(Priority.MEDIUM)
                .build();

        Pageable pageable = PageRequest.of(0,4,Sort.by("id").ascending());
        Page<Colis> colisPage = new PageImpl<>(List.of(savedColis),pageable,1);


        when(colisRepository.findColisByPriorite(colisfilter.getPriority(),pageable)).thenReturn(colisPage);
        when(colisRespMapper.toRespDto(savedColis)).thenReturn(expectedResponse);

        Page<ColisRespDTO> result =  colisService.filter(colisfilter,pageable);

        assertThat(result.getContent()).isEqualTo(List.of(expectedResponse));
        assertThat(colisfilter.getZoneId()).isNull();
        assertThat(colisfilter.getStatus()).isNull();
        assertThat(colisfilter.getPriority()).isNotNull();

        verify(colisRepository,never()).findAll();
        verify(colisRepository).findColisByPriorite(colisfilter.getPriority(),pageable);
        verify(colisRepository,never()).findColisByStatut(any(),any());
        verify(colisRepository,never()).findColisByStatut(any(),any());


    }

}
