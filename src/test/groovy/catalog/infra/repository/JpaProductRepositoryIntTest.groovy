package catalog.infra.repository

import com.myshop.SpringIntTestConfig
import com.myshop.catalog.domain.category.CategoryId
import com.myshop.catalog.domain.product.*
import com.myshop.common.model.Money
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import javax.persistence.EntityManager

/**
 * Created by bluepoet on 2016. 6. 29..
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

    def "상품이미지를 지우고 상품정보를 확인한다."() {
        given:
        def product = productRepository.findById(new ProductId("prod-002"))

        when:
        def newImages = Collections.emptyList()
        product.changeImages(newImages)
        flush()

        then:
        def rows = jdbcTemplate.queryForList(
                "select * from image where product_id = ? order by list_idx asc", "prod-002")
        rows.size() == 0
    }

    def "상품을 삭제하고 실제 정보가 없는지 확인한다."() {
        given:
        def product = productRepository.findById(new ProductId("prod-001"))

        when:
        productRepository.remove(product)
        flush()

        then:
        def productCnt = jdbcTemplate.queryForObject(
                "select count(*) from product where product_id = ?",
                Number.class,
                "prod-001"
        )
        productCnt.intValue() == 0

        def imageCount = jdbcTemplate.queryForObject(
                "select count(*) from image where product_id = ?",
                Number.class,
                "prod-001"
        )
        imageCount.intValue() == 0
    }

    def "특정카테고리 상품을 정해진 페이지 갯수만큼 가져와 확인한다."() {
        when:
        def productsInCategory2001 = productRepository.findByCategoryId(new CategoryId(2001L), 1, 5)

        then:
        productsInCategory2001.size() == 2
        productsInCategory2001.get(0).getId() == new ProductId("prod-003")
        productsInCategory2001.get(1).getId() == new ProductId("prod-002")

        when:
        def productsInCat1001 = productRepository.findByCategoryId(new CategoryId(1001L), 1, 5)

        then:
        productsInCat1001.size() == 2
    }

    def "카테고리별 상품갯수를 확인한다."() {
        when:
        def totalProductsInCategory1001 = productRepository.countsByCategoryId(new CategoryId(1001L))

        then:
        totalProductsInCategory1001 == 2

        when:
        def totalProductsInCategory2001 = productRepository.countsByCategoryId(new CategoryId(2001L))

        then:
        totalProductsInCategory2001 == 2
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