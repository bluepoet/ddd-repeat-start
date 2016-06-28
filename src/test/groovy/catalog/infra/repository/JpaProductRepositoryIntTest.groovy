package catalog.infra.repository

import com.myshop.SpringIntTestConfig
import com.myshop.catalog.domain.product.*
import com.myshop.common.model.Money
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.persistence.EntityManager

/**
 * Created by Mac on 2016. 6. 29..
 */
@SpringIntTestConfig
@Transactional
@Rollback(false)
class JpaProductRepositoryIntTest extends Specification {
    @Autowired
    ProductRepository productRepository

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    EntityManager entityManager

    def "prod-001 상품을 찾아 정보를 확인한다."() {
        when:
        def product = productRepository.findById(new ProductId("prod-001"))

        then:
        product != null
        product.getImages().size() == 2
        product.getImages().get(0) instanceof InternalImage
        product.getImages().get(1) instanceof ExternalImage
    }

    def "모든 상품의 갯수를 확인한다."() {
        when:
        def products = productRepository.findAll()

        then:
        products.size() == 3
    }

    def "새로운 상품을 만들고 정보를 확인한다."() {
        given:
        def product = new Product(new ProductId("prod-999"), "999", new Money(999), "detail",
                Arrays.asList(new ExternalImage("externalPath"),
                        new InternalImage("internal1"),
                        new InternalImage("internal2")))

        when:
        productRepository.save(product)
        flush()

        then:
        def rows = jdbcTemplate.queryForList(
                "select * from image where product_id = ? order by list_idx asc", "prod-999")

        rows.size() == 3
        def firstImage = rows.get(0)
        def sencondImage = rows.get(1)
        def thirdImage = rows.get(2)

        verifyImageType(firstImage.get("image_type"), "EI")
        verifyImageType(sencondImage.get("image_type"), "II")
        verifyImageType(thirdImage.get("image_type"), "II")

        verifyImagePath(firstImage.get("image_path"), "externalPath")
        verifyImagePath(sencondImage.get("image_path"), "internal1")
        verifyImagePath(thirdImage.get("image_path"), "internal2")
    }

    def flush() {
        entityManager.flush()
    }

    def verifyImageType(type, expectedType) {
        type == expectedType
    }

    def verifyImagePath(path, expectedPath) {
        path == expectedPath
    }
}