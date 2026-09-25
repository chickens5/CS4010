package com.packt.cardatabase;

import com.packt.cardatabase.hw2.models.Owner;
import com.packt.cardatabase.hw2.repos.OwnerRepository;
import com.packt.cardatabase.hw2.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner("John", "Doe");
        owner.setOwnerid(1L);
    }

    @Test
    void testGetOwners() {
        when(ownerRepository.findAll()).thenReturn(Arrays.asList(owner));

        List<Owner> owners = ownerService.getOwners();
        assertThat(owners).hasSize(1);
        assertThat(owners.get(0).getFirstname()).isEqualTo("John");
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void testGetOwnerById() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        Optional<Owner> foundOwner = ownerService.getOwnerById(1L);
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getLastname()).isEqualTo("Doe");
        verify(ownerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddOwner() {
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        Owner created = ownerService.addOwner(owner);
        assertThat(created).isNotNull();
        assertThat(created.getFirstname()).isEqualTo("John");
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    void testUpdateOwnerSuccess() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        Owner updateInfo = new Owner("Jane", "Smith");
        Owner updated = ownerService.updateOwner(1L, updateInfo);

        assertThat(updated).isNotNull();
        assertThat(updated.getFirstname()).isEqualTo("Jane");
        assertThat(updated.getLastname()).isEqualTo("Smith");
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void testUpdateOwnerNotFound() {
        when(ownerRepository.findById(99L)).thenReturn(Optional.empty());

        Owner updateInfo = new Owner("Jane", "Smith");
        assertThatThrownBy(() -> ownerService.updateOwner(99L, updateInfo))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does not exist");
    }

    @Test
    void testDeleteOwnerSuccess() {
        when(ownerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ownerRepository).deleteById(1L);

        ownerService.deleteOwner(1L);
        verify(ownerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOwnerNotFound() {
        when(ownerRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> ownerService.deleteOwner(99L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does not exist");
    }
}
