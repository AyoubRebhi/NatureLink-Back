package com.example.naturelink.Service;

import com.example.naturelink.Entity.Menu;
import com.example.naturelink.Repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public Optional<Menu> updateMenu(Long id, Menu menuDetails) {
        return menuRepository.findById(id).map(existingMenu -> {
            existingMenu.setPlats(menuDetails.getPlats());
            existingMenu.setPrixMoyen(menuDetails.getPrixMoyen());
            existingMenu.setRestaurant(menuDetails.getRestaurant());
            return menuRepository.save(existingMenu);
        });
    }

    @Override
    public boolean deleteMenu(Long id) {
        return menuRepository.findById(id).map(menu -> {
            menuRepository.delete(menu);
            return true;
        }).orElse(false);
    }
}