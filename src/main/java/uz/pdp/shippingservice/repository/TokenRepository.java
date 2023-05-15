package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
