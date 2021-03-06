/*
 * Copyright (C) 2015 Arthur Gregorio, AG.Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.webbudget.domain.entities.registration;

import br.com.webbudget.domain.entities.PersistentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The representation of a movement class in the application
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 04/03/2014
 */
@Entity
@Audited
@Table(name = "movement_classes")
@ToString(callSuper = true, of = "name")
@AuditTable(value = "audit_movement_classes")
@EqualsAndHashCode(callSuper = true, of = "name")
public class MovementClass extends PersistentEntity {

    @Getter
    @Setter
    @NotEmpty(message = "{movement-class.name}")
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Getter
    @Setter
    @Column(name = "budget")
    @NotNull(message = "{movement-class.budget}")
    private BigDecimal budget;
    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "movement_class_type", nullable = false)
    @NotNull(message = "{movement-class.movement-class-type}")
    private MovementClassType movementClassType;

    @Getter
    @Setter
    @ManyToOne
    @NotNull(message = "{movement-class.cost-center}")
    @JoinColumn(name = "id_cost_center", nullable = false)
    private CostCenter costCenter;

    @Getter
    @Setter
    @Transient
    private BigDecimal totalMovements;

    /**
     * Default constructor
     */
    public MovementClass() {
        this.active = true;
        this.budget = BigDecimal.ZERO;
        this.totalMovements = BigDecimal.ZERO;
    }
    
    /**
     * To check if is a revenue class
     *
     * @return <code>true</code> for revenue class, <code>false</code> otherwise
     */
    public boolean isRevenues() {
        return this.movementClassType == MovementClassType.REVENUE;
    }
    
    /**
     * To check if is a expenses class
     * 
     * @return <code>true</code> for expenses class, <code>false</code> otherwise
     */
    public boolean isExpenses() {
        return this.movementClassType == MovementClassType.EXPENSE;
    }
    
    /**
     * Method to check if the budget of this class is over the maximum value
     *
     * @return <code>true</code> if the budget is over the maximum, <code>false</code> otherwise
     */
    public boolean isOverBudget() {
        return this.totalMovements.compareTo(this.budget) >= 0;
    }

    /**
     * Method used in the UI to draw the graphic to show the user how many percents of the class budget is already used
     *
     * @return the budget consume percentage
     */
    public int budgetCompletionPercentage() {
        
        BigDecimal percentage = BigDecimal.ZERO;
        
        if (this.isOverBudget()) {
            return 100;
        } else if (this.budget.equals(BigDecimal.ZERO)) {
            percentage = this.totalMovements.multiply(new BigDecimal(100))
                            .divide(this.budget, 2, RoundingMode.HALF_UP);
        }
        return percentage.intValue() > 100 ? 100 : percentage.intValue();
    }
}
