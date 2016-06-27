import com.myshop.SpringIntTestConfig
import com.myshop.board.domain.Article
import com.myshop.board.domain.ArticleContent
import com.myshop.board.domain.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification


/**
 * Created by Mac on 2016. 6. 28..
 */
@SpringIntTestConfig
@Transactional
@Rollback(false)
class JpaArticleRepositoryTest extends Specification {
    @Autowired
    ArticleRepository articleRepository

    @Autowired
    JdbcTemplate jdbcTemplate

    def "저장된 게시글을 확인한다."() {
        given:
        def article = new Article("제목", new ArticleContent("content", "type"))

        when:
        articleRepository.save(article)

        then:
        def savedArticleId = article.getId()
        def rows = jdbcTemplate.queryForList(
                "select * from article_content where id = ?", savedArticleId)

        savedArticleId.longValue() == ((Number) rows.get(0).get("id")).longValue()
    }

    def "특정 게시글이 있는지 확인한다."() {
        when:
        def article = articleRepository.findById(1L);

        then:
        article != null
    }
}