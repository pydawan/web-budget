/*
 * Copyright (C) 2017 Arthur Gregorio, AG.Software
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
package br.com.webbudget.domain.repositories.tools;

import br.com.webbudget.domain.entities.tools.Group;
import br.com.webbudget.domain.entities.tools.Group_;
import br.com.webbudget.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Optional;

/**
 * The {@link Group} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 3.0.0, 28/12/2017
 */
@Repository
public interface GroupRepository extends DefaultRepository<Group> {

    /**
     * Fin a {@link Group} by the name
     * 
     * @param name the name of the {@link Group} to search
     * @return an {@link Optional} of the {@link Group}
     */
    Optional<Group> findOptionalByName(String name);
    
    /**
     * {@inheritDoc}
     * 
     * @return 
     */
    @Override
    default SingularAttribute<Group, Boolean> getEntityStateProperty() {
        return Group_.active;
    }

    /**
     * {@inheritDoc}
     * 
     * @param filter
     * @return 
     */
    @Override
    default Criteria<Group, Group> getRestrictions(String filter) {
        return criteria()
                .likeIgnoreCase(Group_.name, filter);
    }
}
