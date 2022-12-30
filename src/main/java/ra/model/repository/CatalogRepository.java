package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Catalog;

import java.util.List;
@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Integer> {
    Page<Catalog> findByCatalogNameContaining(String name, Pageable pageable);
    @Query(value = "WITH recursive TEMPDATA(catalogId,catalogDescription,catalogImg ,catalogName,catalogStatus,parentId,parentName)\n" +
            "                       AS (SELECT a.catalogId,\n" +
            "                                  a.catalogDescription,\n" +
            "                                  a.catalogImg,\n" +
            "                                  a.catalogName,\n" +
            "                                  a.catalogStatus,\n" +
            "                                  a.parentId,\n" +
            "                                  a.parentName\n" +
            "                           FROM catalog a\n" +
            "                           WHERE catalogId = :catId\n" +
            "                           union all\n" +
            "                           select child.catalogId,child.catalogDescription,child.catalogImg ,child.catalogName,child.catalogStatus,child.parentId,child.parentName\n" +
            "                           from TEMPDATA p\n" +
            "                                    inner join catalog child on p.catalogId = child.parentId)\n" +
            "    SELECT *\n" +
            "    FROM TEMPDATA;",nativeQuery = true)
    List<Catalog> findChildById(@Param("catId")int catId);
    @Query(value = "select cat.catalogId,cat.catalogDescription,cat.catalogImg ,cat.catalogName,cat.catalogStatus,cat.parentId,cat.parentName\n" +
            "    from catalog cat\n" +
            "    where cat.catalogStatus = 1\n" +
            "      and cat.catalogId not in (select c.catalogId\n" +
            "                                from catalog c\n" +
            "                                         inner join product p on c.catalogId = p.catalogId);\n",nativeQuery = true)
    List<Catalog> getCatalogForCreat();
    @Query(value = "select  child.catalogId,child.catalogDescription,child.catalogImg,child.catalogName,child.catalogStatus,child.parentId,child.parentName\n" +
            "from catalog child\n" +
            "where child.catalogId not in (select p.catalogId\n" +
            "                              from catalog p inner join catalog c on p.catalogId=c.parentId\n" +
            "                              group by p.catalogId );",nativeQuery = true)
    List<Catalog> getCatCreatProduct();
}
