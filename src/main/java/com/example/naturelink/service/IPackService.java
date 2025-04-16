package com.example.naturelink.service;

import com.example.naturelink.dto.PackDTO;
import com.example.naturelink.entity.Pack;

import java.util.List;
import java.util.Optional;

public interface IPackService {

    List<PackDTO> getAllPacks();

    Optional<Pack> getPackById(Long id);

    Pack addPack(PackDTO packDTO);

    Pack updatePack(Long id, PackDTO packDTO);

    void deletePack(Long id);
}
