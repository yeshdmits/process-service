package com.yeshenko.process.domain.entity;

import static com.yeshenko.process.domain.entity.TestData.TestDataColumns.TABLE_NAME;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = TABLE_NAME)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestData {

  public static final class TestDataColumns {

    public static final String TABLE_NAME = "test_data";
    public static final String ID_SEQ = "test_data_id_seq";
    public static final String VALUE = "value";

    private TestDataColumns() {
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = TestDataColumns.ID_SEQ)
  private Long id;
  @Column(name = TestDataColumns.VALUE)
  private Boolean value;
}
