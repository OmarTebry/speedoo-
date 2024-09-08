package com.transfer.speedotransfer.controller;

import com.transfer.speedotransfer.dto.CreateFavoriteDTO;
import com.transfer.speedotransfer.dto.FavoriteDTO;
import com.transfer.speedotransfer.exception.custom.ResourceNotFoundException;
import com.transfer.speedotransfer.exception.response.ErrorDetails;
import com.transfer.speedotransfer.service.IFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
@Validated
@Tag(name = "Favorite Controller", description = "Favorite controller")
public class FavoriteController {

    private final IFavoriteService favoriteService;

    @Operation(summary = "Add new Favorite")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = FavoriteDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @PostMapping
    public FavoriteDTO addFavorite(@Valid @RequestBody CreateFavoriteDTO createFavoriteDTO) throws ResourceNotFoundException {
        return this.favoriteService.addFavorite(createFavoriteDTO);
    }

    @Operation(summary = "Get Favorites by User Id")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = FavoriteDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @GetMapping("/user/{userId}")
    public List<FavoriteDTO> getFavoritesByUserId(@PathVariable Long userId) throws ResourceNotFoundException {
        return this.favoriteService.getFavoritesById(userId);
    }

    @Operation(summary = "Delete Favorite by Id")
    @ApiResponse(responseCode = "200", description = "Favorite deleted successfully")
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorDetails.class), mediaType = "application/json")})
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long favoriteId) throws ResourceNotFoundException {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.ok().build();  // Return HTTP 200 with no content
    }
}
