/*
 * Copyright (C) 2013 Arthur Gregorio, AG.Software
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
package br.com.webbudget.domain.repositories.registration;

import br.com.webbudget.domain.entities.registration.Wallet;
import br.com.webbudget.domain.entities.registration.WalletBalance;
import br.com.webbudget.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

/**
 * The {@link WalletBalance} repository
 *
 * @author Arthur Gregorio
 *
 * @version 2.1.0
 * @since 1.0.0, 04/03/2013
 */
@Repository
public interface WalletBalanceRepository extends DefaultRepository<WalletBalance> {

    /**
     * Method to find all the {@link WalletBalance} of the given {@link Wallet}
     *
     * @param walletId the id of the {@link Wallet} to search
     * @return the list of {@link WalletBalance} for this wallet
     */
    List<WalletBalance> findByWallet_id(long walletId);
}
