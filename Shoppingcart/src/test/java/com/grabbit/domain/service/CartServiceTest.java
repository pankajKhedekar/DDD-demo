package com.grabbit.domain.service;

import com.grabbit.domain.aggregate.Cart;
import com.grabbit.domain.dto.ProductDTO;
import com.grabbit.domain.repository.CartRepository;
import com.grabbit.domain.repository.ProductRepository;
import com.grabbit.domain.tax.TaxPolicy;
import com.grabbit.domain.valueobject.CartItem;
import com.grabbit.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TaxPolicy taxPolicy;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private CartItem cartItem1;

    @BeforeEach
    void setUp() {
        cart = new Cart("cart1");
        cartItem1 = new CartItem("product1", 2);

        lenient().when(cartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        lenient().when(productRepository.fetchProductById("product1"))
                .thenReturn(Optional.of(new ProductDTO("product1", "Sample ProductDTO", new Money(BigDecimal.TEN))));
        lenient().when(taxPolicy.calculateTax(any(Money.class)))
                .thenReturn(new Money(BigDecimal.valueOf(2.0)));
    }

    @Test
    void whenAddProductsToCart_thenCartSubtotalTaxAndTotalShouldBeCalculatedCorrectly() {
        var cartItem2 = new CartItem("product2", 4);
        var product2 = new ProductDTO(cartItem2.getProductId(), cartItem2.getProductId(), new Money(new BigDecimal("2.52")));

        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));
        when(productRepository.fetchProductById(cartItem2.getProductId())).thenReturn(Optional.of(product2));

        var result = cartService.addProductsToCart("cart1", List.of(cartItem1, cartItem2));

        verify(cartRepository).findById("cart1");
        verify(cartRepository).save(cart);

        assertNotNull(result);
        assertTrue(result.items().contains(cartItem1));
        assertTrue(result.items().contains(cartItem2));
        assertEquals(2, result.items().size());
        assertEquals(new Money(BigDecimal.valueOf(30.08)), result.subtotal());
        assertEquals(new Money(BigDecimal.valueOf(2.0)), result.tax());
        assertEquals(new Money(BigDecimal.valueOf(32.08)), result.total());
    }

    @Test
    void whenProductNotFound_thenThrowIllegalArgumentException() {
        when(productRepository.fetchProductById("product1"))
                .thenReturn(empty());

        var exception = assertThrows(IllegalArgumentException.class, () -> cartService.addProductsToCart("cart1", List.of(cartItem1)));

        assertEquals("Product not found: product1", exception.getMessage());
    }

    @Test
    void whenAddingProductsToNewCart_thenCartShouldBeCreated() {
        var newCartId = "newCart";
        when(cartRepository.findById(newCartId)).thenReturn(empty());

        var result = cartService.addProductsToCart(newCartId, List.of(cartItem1));

        verify(cartRepository).save(any(Cart.class));
        assertEquals(newCartId, result.cartId());
    }

    @Test
    void whenCartFoundById_thenReturnCartState() {
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        cartService.addProductsToCart(cart.getId(), List.of(cartItem1));

        var result = cartService.getCart(cart.getId());

        assertNotNull(result);
        assertIterableEquals(List.of(cartItem1), result.items());
        assertEquals(new Money(BigDecimal.valueOf(20.0)), result.subtotal());
        assertEquals(new Money(BigDecimal.valueOf(2.0)), result.tax());
        assertEquals(new Money(BigDecimal.valueOf(22.0)), result.total());
    }

    @Test
    void whenCartNotFoundById_thenThrowException() {
        String cartId = "cart123";
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> cartService.getCart(cartId));
        assertEquals("Cart not found: cart123", thrown.getMessage());
        verify(cartRepository, times(1)).findById(cartId);
    }
}
