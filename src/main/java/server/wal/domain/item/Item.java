package server.wal.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import server.wal.domain.category.Category;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String contents;

    @Column(name = "voice_url")
    private String voiceUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private double categoryItemNumber; // 카테고리별 유니크한 번호

    @Builder(access = AccessLevel.PACKAGE)
    public Item(Category category, String contents, String voiceUrl, String imageUrl, double categoryItemNumber) {
        this.category = category;
        this.contents = contents;
        this.voiceUrl = voiceUrl;
        this.imageUrl = imageUrl;
        this.categoryItemNumber = categoryItemNumber;
    }

    public static Item newInstance(Category category, String contents, @Nullable String voiceUrl, @Nullable String imageUrl, double itemSize) {
        double categoryItemNumber = itemSize + 1;
        return Item.builder()
                .category(category)
                .contents(contents)
                .voiceUrl(voiceUrl)
                .imageUrl(imageUrl)
                .categoryItemNumber(categoryItemNumber)
                .build();
    }

}
