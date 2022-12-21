package app.test.barogojwt.domain.deliveryservice.infrastructure.domain.shop;

import app.test.barogojwt.domain.deliveryservice.domain.shop.Shop;
import app.test.barogojwt.domain.deliveryservice.domain.shop.ShopRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class ShopRepositoryImpl implements ShopRepository {

    private final ShopJpaRepository shopJpaRepository;
    @Override
    public Shop save(Shop shop) {
        return shopJpaRepository.save(shop);
    }

    @Override
    public Optional<Shop> getShopById(long id) {
        return shopJpaRepository.findShopById(id);
    }
}
