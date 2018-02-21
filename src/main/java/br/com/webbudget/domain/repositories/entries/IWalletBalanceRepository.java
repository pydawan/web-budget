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
package br.com.webbudget.domain.repositories.entries;

import br.com.webbudget.domain.entities.entries.Wallet;
import br.com.webbudget.domain.entities.entries.WalletBalance;
import br.com.webbudget.domain.entities.entries.WalletBalanceType;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 2.0.0
 * @since 1.0.0, 04/03/2013
 */
@Repository
public interface IWalletBalanceRepository extends EntityRepository<WalletBalance, Long> {

    /**
     *
     * @param wallet
     * @return
     */
    public WalletBalance findLastWalletBalance(Wallet wallet);

    /**
     *
     * @param type
     * @return
     */
    public List<WalletBalance> listByType(WalletBalanceType type);

    /**
     *
     * @param source
     * @param target
     * @param types
     * @return
     */
    public List<WalletBalance> listByWallet(Wallet source, Wallet target, WalletBalanceType... types);
}