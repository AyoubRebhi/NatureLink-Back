package com.example.naturelink.services;

import com.example.naturelink.entity.Pack;

import java.util.List;
import java.util.Optional;

public interface IPackService {
    List<Pack> getAllPacks();
    Optional<Pack> getPackById(Long id);
    Pack addPack(Pack pack);
    Pack updatePack(Long id, Pack pack);
    void deletePack(Long id);
}
