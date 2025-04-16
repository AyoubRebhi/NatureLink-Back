package com.example.naturelink.Service;

import com.example.naturelink.Entity.Menu;

import java.util.List;
import java.util.Optional;

public interface IMenuService {

    Menu createMenu(Menu menu);

    List<Menu> getAllMenus();

    Optional<Menu> getMenuById(Long id);

    Optional<Menu> updateMenu(Long id, Menu menuDetails);

    boolean deleteMenu(Long id);
}