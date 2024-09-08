package com.transfer.speedotransfer.service;

import com.transfer.speedotransfer.dto.CreateFavoriteDTO;
import com.transfer.speedotransfer.dto.FavoriteDTO;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;

import java.util.List;

public interface IFavoriteService {
    FavoriteDTO addFavorite(CreateFavoriteDTO createFavoriteDTO)throws ResourceNotFoundException;
    List<FavoriteDTO> getFavoritesById(Long userId) throws ResourceNotFoundException;
    void deleteFavorite(Long favoriteId) throws ResourceNotFoundException;
}
