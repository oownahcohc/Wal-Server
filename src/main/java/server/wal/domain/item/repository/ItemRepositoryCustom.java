package server.wal.domain.item.repository;

import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.item.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    List<Item> findAllByCategoryType(WalCategoryType categoryType);
    Item findFirstItemByCategoryType(WalCategoryType categoryType);
    Item findItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId);

}
