package com.github.johnnysc.dayswithoutbadhabits.data

import com.github.johnnysc.dayswithoutbadhabits.Now
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.domain.Repository

/**
 * @author Asatryan on 18.12.2022
 */
class NewRepository(
    private val cacheDataSource: NewCacheDataSource,
    private val now: Now,
    private val mapper: CardMapper<Card> = CardMapper.Base(now)
) : Repository {

    override fun cards(): List<Card> {
        val cacheList = cacheDataSource.cards()
        return cacheList.map { it.map(mapper) }
    }

    override fun newCard(text: String): Card {
        val id = now.time()
        cacheDataSource.addCard(id, text)
        return Card.ZeroDays(text, id)
    }

    override fun updateCard(id: Long, newText: String) = cacheDataSource.updateCard(id, newText)

    override fun deleteCard(id: Long) = cacheDataSource.deleteCard(id)

    override fun resetCard(id: Long) = cacheDataSource.resetCard(id, now.time())
}