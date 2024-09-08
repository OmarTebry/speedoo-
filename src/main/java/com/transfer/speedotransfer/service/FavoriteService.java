package com.transfer.speedotransfer.service;

import com.transfer.speedotransfer.entity.Favorite;
import com.transfer.speedotransfer.dto.CreateFavoriteDTO;
import com.transfer.speedotransfer.dto.FavoriteDTO;
import com.transfer.speedotransfer.entity.Account;
import com.transfer.speedotransfer.entity.User;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.repository.AccountRepository;
import com.transfer.speedotransfer.repository.FavoriteRepository;
import com.transfer.speedotransfer.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService implements IFavoriteService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional
    public FavoriteDTO addFavorite(CreateFavoriteDTO createFavoriteDTO) throws ResourceNotFoundException {
        // Fetch user from the repository, throw an exception if not found
        User user = userRepository.findById(createFavoriteDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + createFavoriteDTO.getUserId() + " not found"));

        // Validate account number and name
        Account account = accountRepository.findByAccountNumber(createFavoriteDTO.getAccountNum())
                .orElseThrow(() -> new ResourceNotFoundException("Account with number " + createFavoriteDTO.getAccountNum() + " not found"));

        // Validate account name (assuming the account name needs to match the provided name)
        if (!account.getAccountName().equals(createFavoriteDTO.getAccountName())) {
            throw new IllegalArgumentException("Account name does not match the provided account number");
        }

        boolean isUserAccount = user.getAccounts().stream()
                .anyMatch(userAccount -> userAccount.getAccountNumber().equals(createFavoriteDTO.getAccountNum()));

        if (isUserAccount) {
            throw new IllegalArgumentException("You cannot add your own account as a favorite.");
        }

        boolean isAlreadyFavorite = favoriteRepository.existsByUserAndAccountNumber(user, createFavoriteDTO.getAccountNum());
        if (isAlreadyFavorite) {
            throw new IllegalArgumentException("This account is already in your favorites.");
        }

        // Create new favorite entry
        Favorite favorite = Favorite.builder()
                .user(user)
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .build();

        // Save favorite to repository
        Favorite savedFavorite = favoriteRepository.save(favorite);

        // Return the saved favorite as DTO
        return savedFavorite.toDTO();
    }

    @Override
    public List<FavoriteDTO> getFavoritesById(Long userId) throws ResourceNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);

        return favorites.stream()
                .map(Favorite::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFavorite(Long favoriteId) throws ResourceNotFoundException {

        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite with id " + favoriteId + " not found"));

        favoriteRepository.deleteById(favoriteId);
    }
}
