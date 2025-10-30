package org.example.smart_delivery.mapper;

import org.example.smart_delivery.entity.*;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class RefMapper {
    @Named("toUserRef")
    public User toUserRef(String id){
        if (id == null) return null;
        User u = new User();
        u.setId(id);
        return u;
    }

    @Named("toLivreurRef")
    public Livreur toLivreurRef(String id) {
        if (id == null) return null;
        Livreur l = new Livreur();
        l.setId(id);
        return l;
    }

    @Named("toZoneRef")
    public Zone toZoneRef(String id) {
        if (id == null) return null;
        Zone z = new Zone();
        z.setId(id);
        return z;
    }

    @Named("toColisRef")
    public Colis toColisRef(String id) {
        if (id == null) return null;
        Colis c = new Colis();
        c.setId(id);
        return c;
    }

    @Named("toProduitRef")
    public Produit toProduitRef(String id) {
        if (id == null) return null;
        Produit p = new Produit();
        p.setId(id);
        return p;
    }

    @Named("toId")
    public String toId(User u) { return u == null ? null : u.getId(); }

    @Named("toIdLivreur")
    public String toId(Livreur l) { return l == null ? null : l.getId(); }

    @Named("toIdZone")
    public String toId(Zone z) { return z == null ? null : z.getId(); }

    @Named("toColisId")
    public String toColisId(Colis c) { return c == null ? null : c.getId(); }

    @Named("toProduitId")
    public String toProduitId(Produit p) { return p == null ? null : p.getId(); }
}
