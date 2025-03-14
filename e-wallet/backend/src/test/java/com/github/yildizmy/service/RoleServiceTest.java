package com.github.yildizmy.service;

import com.github.yildizmy.domain.entity.Role;
import com.github.yildizmy.domain.enums.RoleType;
import com.github.yildizmy.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void getReferenceByTypeIsIn_shouldReturnRolesForGivenTypes() {
        Set<RoleType> types = new HashSet<>(Arrays.asList(RoleType.ROLE_ADMIN, RoleType.ROLE_USER));
        List<Role> expectedRoles = Arrays.asList(
                createRole(1L, RoleType.ROLE_ADMIN),
                createRole(2L, RoleType.ROLE_USER)
        );
        when(roleRepository.getReferenceByTypeIsIn(types)).thenReturn(expectedRoles);

        List<Role> result = roleService.getReferenceByTypeIsIn(types);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(role -> role.getType() == RoleType.ROLE_ADMIN));
        assertTrue(result.stream().anyMatch(role -> role.getType() == RoleType.ROLE_USER));
        verify(roleRepository, times(1)).getReferenceByTypeIsIn(types);
    }

    @Test
    void getReferenceByTypeIsIn_shouldReturnEmptyListForNonExistentTypes() {
        Set<RoleType> nonExistentTypes = new HashSet<>(Collections.emptySet());
        when(roleRepository.getReferenceByTypeIsIn(nonExistentTypes)).thenReturn(Collections.emptyList());

        List<Role> result = roleService.getReferenceByTypeIsIn(nonExistentTypes);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository, times(1)).getReferenceByTypeIsIn(nonExistentTypes);
    }

    @Test
    void findAll_shouldReturnAllRoles() {
        List<Role> expectedRoles = Arrays.asList(
                createRole(1L, RoleType.ROLE_ADMIN),
                createRole(2L, RoleType.ROLE_USER)
        );
        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<Role> result = roleService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedRoles, result);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void findAll_shouldReturnEmptyListWhenNoRolesExist() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());

        List<Role> result = roleService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository, times(1)).findAll();
    }

    private Role createRole(Long id, RoleType type) {
        Role role = new Role();
        role.setId(id);
        role.setType(type);
        return role;
    }
}
