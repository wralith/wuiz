package com.example.wuiz.result;

import com.example.wuiz.quiz.Quiz;
import jakarta.persistence.*;
import lombok.*;

// Can be a module by itself maybe?
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Result {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  // TODO(wralith): Should be replaced with User
  private String owner;

  // TODO(wralith): Just an integer for now, There can be replication of quiz with mistakes and
  // correct answers marked
  /** Quiz result point over 100 */
  @Builder.Default
  private int score = 0;

  @OneToOne private Quiz quiz;

  public void increasePoint(int value) {
    this.score += value;
    if (this.score > 100) {
      this.score = 100;
    }
  }
}
