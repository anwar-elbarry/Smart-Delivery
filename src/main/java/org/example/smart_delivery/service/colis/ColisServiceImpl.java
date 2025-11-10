package org.example.smart_delivery.service.colis;

import ch.qos.logback.core.util.COWArrayList;
import org.example.smart_delivery.dto.request.ColisDTO;
import org.example.smart_delivery.dto.request.ColisProduitDTO;
import org.example.smart_delivery.dto.request.HistoriqueLivraisonDTO;
import org.example.smart_delivery.dto.response.ColisRespDTO;
import org.example.smart_delivery.entity.*;
import org.example.smart_delivery.entity.enums.ColisStatus;
import org.example.smart_delivery.entity.enums.Priority;
import org.example.smart_delivery.exception.ResourceNotFoundException;
import org.example.smart_delivery.mapper.request.ColisMapper;
import org.example.smart_delivery.mapper.request.ColisProduitMapper;
import org.example.smart_delivery.mapper.request.HistoLivrMapper;
import org.example.smart_delivery.mapper.response.ColisRespMapper;
import org.example.smart_delivery.mapper.response.HistoLivrRespMapper;
import org.example.smart_delivery.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {
    private final ColisRepository colisRepository;
    private final ColisMapper colisMapper;
    private final LivreurRepository livreurRepository;
    private final UserRepository userRepository;
    private final ProduitRepository produitRepository;
    private final ColisProduitRepository colisProduitRepository;
    private final ColisProduitMapper colisProduitMapper;
    private final ColisRespMapper colisRespMapper;
    private final HistoLivrMapper histoLivrMapper;
    private final ZoneRepository zoneRepository;
    private final HistoriqueLivraisonRepository historiqueLivraisonRepository;

    @Override
    public ColisRespDTO create(ColisDTO dto) {
        Colis entity = colisMapper.toEntity(dto);
        entity.setLivreur(null);
        entity.setStatut(ColisStatus.CREATED);
        Colis saved = colisRepository.save(entity);
        HistoriqueLivraisonDTO historiqueLivraisonDTO = new HistoriqueLivraisonDTO();
        historiqueLivraisonDTO.setColisId(saved.getId());
        historiqueLivraisonDTO.setStatut(ColisStatus.CREATED);
        historiqueLivraisonDTO.setDateChangement(LocalDateTime.now());
        historiqueLivraisonDTO.setCommentaire("create colis");
        historiqueLivraisonRepository.save(histoLivrMapper.toEntity(historiqueLivraisonDTO));
        return colisRespMapper.toRespDto(saved);
    }

    @Override
    public ColisRespDTO update(String id, ColisDTO dto) {
        if (!colisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Colis",id);
        }
        Colis entity = colisMapper.toEntity(dto);
        entity.setId(id);
        Colis saved = colisRepository.save(entity);
        return colisRespMapper.toRespDto(saved);
    }

    @Override
    public ColisRespDTO getById(String id) {
        Colis entity = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis",id));
        return colisRespMapper.toRespDto(entity);
    }

    @Override
    public Page<ColisRespDTO> getAll(Pageable pageable) {
        return colisRepository.findAll(pageable)
                .map(colisRespMapper::toRespDto);
    }

    @Override
    public void delete(String id) {
        if (!colisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Colis",id);
        }
        colisRepository.deleteById(id);
    }

    @Override
    public void Assign_col(String colisId,String livreurId) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis",colisId));
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new ResourceNotFoundException("Livreur",livreurId));
        colis.setLivreur(livreur);
        colis.setStatut(ColisStatus.COLLECTED);
        colisRepository.save(colis);
    }

    @Override
    public Page<ColisRespDTO> filter(Colisfilter colisfilter,Pageable pageable) {
        if (colisfilter == null) {
            return colisRepository.findAll(pageable).map(colisRespMapper::toRespDto);
        }

        // Filter by zone
        if (colisfilter.getZoneId() != null && !colisfilter.getZoneId().isBlank()) {
            Zone zone = zoneRepository.findById(colisfilter.getZoneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zone", colisfilter.getZoneId()));
            return colisRepository
                    .findColisByZone(zone, pageable)
                    .map(colisRespMapper::toRespDto);
        }

        // Filter by status
        if (colisfilter.getStatus() != null) {
            return colisRepository
                    .findColisByStatut(colisfilter.getStatus(), pageable)
                    .map(colisRespMapper::toRespDto);
        }

        // Filter by priority
        if (colisfilter.getPriority() != null) {
            return colisRepository
                    .findColisByPriorite(colisfilter.getPriority(), pageable)
                    .map(colisRespMapper::toRespDto);
        }

        return colisRepository.findAll(pageable).map(colisRespMapper::toRespDto);
    }
    @Override
    public void createColisRequest(String expedId , String distenId,List<String> produitIds){
        User exped = userRepository.findById(expedId).orElseThrow(
                ()->    new ResourceNotFoundException("User",expedId)
        );
        User disten = userRepository.findById(distenId).orElseThrow(
                ()->    new ResourceNotFoundException("User",distenId)
        );
        List<Produit> produits = produitIds.stream()
                .map(id -> produitRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Produit", id)))
                .toList();
        BigDecimal poids = BigDecimal.valueOf(produits.stream().mapToDouble(Produit::getPoids).sum());

        List<String> produitsname = produits.stream().map(Produit::getNom).toList();
        String produitsApercu = produitsname.size() > 5
                ? String.join(", ", produitsname.subList(0, 5)) + ", ..."
                : String.join(", ", produitsname);


        String description = String.format(
                "Colis de l'expéditeur %s vers le destinataire %s — %d produit(s) (%s) — poids total : %.2f g",
                exped.getNom()+" "+exped.getPrenom(),
                disten.getNom()+" "+disten.getPrenom() ,
                produits.size(),
                produitsApercu,
                poids
        );

        int quantity = produits.size();

        ColisDTO colisDTO = new ColisDTO();
        colisDTO.setPoids(poids);
        colisDTO.setPriorite(Priority.MEDIUM);
        colisDTO.setStatut(ColisStatus.CREATED);
        colisDTO.setDescription(description);
        colisDTO.setDestinataireId(distenId);
        colisDTO.setClientExpediteurId(expedId);


        Colis colis = colisRepository.save(colisMapper.toEntity(colisDTO));
        for (Produit produit:  produits){
            ColisProduitDTO cp = new ColisProduitDTO();
            cp.setColisId(colis.getId());
            cp.setProduitId(produit.getId());
            cp.setQuantite(quantity);
            cp.setDateAjout(LocalDateTime.now());
            cp.setPrix(produit.getPrix());
            ColisProduit cpEntity = colisProduitMapper.toEntity(cp);
            colisProduitRepository.save(cpEntity);
        }
    }

    @Override
    public Page<ColisRespDTO> search(String q, Pageable pageable) {
        return colisRepository.search(q,pageable).map(colisRespMapper::toRespDto);
    }

    @Override
    public Coliscounter calcule(String livreurId) {
        return colisRepository.aggregateByLivreurId(livreurId);
    }
}
