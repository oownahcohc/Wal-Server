package server.wal.domain.category;

import lombok.Getter;
import server.wal.domain.common.enumerate.WalCategoryType;
import server.wal.domain.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType categoryType;

    @OneToMany(mappedBy = "category")
    List<Item> items = new ArrayList<>();
}
