package com.github.johnnysc.dayswithoutbadhabits.data

import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.domain.Repository

/**
 * @author Asatryan on 18.12.2022
 */
class BaseRepository(
    private val cacheDataSource: CacheDataSource,
    private val now: Now,
    private val mapper: CardMapper<Card> = CardMapper.Base(now)
) : Repository {

    override fun cards(): List<Card> {
        val cacheList = cacheDataSource.read()
        return cacheList.map { it.map(mapper) }
    }

    override fun newCard(text: String): Card {
        val id = now.time()
        val list = cacheDataSource.read()
        list.add(CardCache(id, id, text))
        cacheDataSource.save(list)
        return Card.ZeroDays(text, id)
    }

    override fun updateCard(id: Long, newText: String) {
        val mutableList = cacheDataSource.read()
        val card = mutableList.find { it.map(CardMapper.Same(id)) }!!
        val index = mutableList.indexOf(card)
        val newCard = card.map(CardMapper.UpdateText(newText))
        mutableList[index] = newCard
        cacheDataSource.save(mutableList)
    }

    override fun deleteCard(id: Long) {
        val cachedCards = cacheDataSource.read()
        val card = cachedCards.find { it.map(CardMapper.Same(id)) }!!
        cachedCards.remove(card)
        cacheDataSource.save(cachedCards)
    }

    override fun resetCard(id: Long) {
        val mutableList = cacheDataSource.read()
        val card = mutableList.find { it.map(CardMapper.Same(id)) }!!
        val index = mutableList.indexOf(card)
        val newCard = card.map(CardMapper.UpdateTime(now.time()))
        mutableList[index] = newCard
        cacheDataSource.save(mutableList)
    }

    override fun moveCardUp(position: Int) {
        val mutableList = cacheDataSource.read()
        val card = mutableList[position]
        mutableList.remove(card)
        mutableList.add(position - 1, card)
        cacheDataSource.save(mutableList)
    }

    override fun moveCardDown(position: Int) {
        val mutableList = cacheDataSource.read()
        val card = mutableList[position]
        mutableList.remove(card)
        mutableList.add(position + 1, card)
        cacheDataSource.save(mutableList)
    }
}