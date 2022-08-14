package server.wal.domain.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.item.Item;

import java.util.List;

import static server.wal.domain.item.QItem.*;

@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Item> findAllByCategoryType(WalCategoryType categoryType) {
        return query
                .selectFrom(item)
                .where(item.category.categoryType.eq(categoryType))
                .fetch();
    }

    @Override
    public Item findFirstItemByCategoryType(WalCategoryType categoryType) {
        return query
                .selectFrom(item)
                .where(item.category.categoryType.eq(categoryType))
                .fetchFirst();
    }

    @Override
    public Item findItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId) {
        return query
                .selectFrom(item)
                .where(
                        item.category.categoryType.eq(categoryType),
                        item.categoryItemNumber.eq(nextItemId)
                ).fetchOne();
    }

}
