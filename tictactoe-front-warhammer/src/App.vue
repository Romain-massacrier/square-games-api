<template>
  <main class="page">
    <section class="codex-panel">
      <header class="hero">
        <p class="seal">Adeptus Archivum</p>
        <h1>Codex Morpionum</h1>
        
      </header>

      <section class="factions" aria-label="Factions en guerre">
        <article class="faction-card imperium" :class="{ active: currentSymbol === 'X' }">
          <span class="faction-icon">✠</span>
          <div>
            <h2>Imperium</h2>
            <p>Jeton X</p>
          </div>
        </article>

        <article class="faction-card chaos" :class="{ active: currentSymbol === 'O' }">
          <span class="faction-icon">☉</span>
          <div>
            <h2>Chaos</h2>
            <p>Jeton O</p>
          </div>
        </article>
      </section>

      <button class="primary" @click="createGame" :disabled="loading">
        {{ loading ? 'Ouverture des archives...' : 'Nouvelle croisade' }}
      </button>

      <div v-if="game" class="infos">
        <p><strong>Archive :</strong> {{ game.gameId }}</p>
        <p><strong>Statut :</strong> {{ statusLabel }}</p>
        <p><strong>Tour :</strong> {{ currentFactionLabel }}</p>
      </div>

      <div class="battlefield" :class="{ locked: !game || loading }">
        <button
          v-for="(cell, index) in cells"
          :key="index"
          class="cell"
          :class="cellClass(cell)"
          :disabled="!game || cell !== '' || gameFinished || loading"
          @click="play(index)"
          :aria-label="`Case ${index + 1}`"
        >
          <span v-if="cell === 'X'">✠</span>
          <span v-else-if="cell === 'O'">☉</span>
          <span v-else class="empty">·</span>
        </button>
      </div>

      <p v-if="message" class="message">{{ message }}</p>

    </section>
  </main>
</template>

<script>
import axios from 'axios'

const API_URL = 'http://localhost:8080'

export default {
  name: 'App',

  data() {
    return {
      game: null,
      cells: Array(9).fill(''),
      currentSymbol: 'X',
      loading: false,
      message: ''
    }
  },

  computed: {
    gameFinished() {
      if (!this.game) return false
      return this.game.status !== 'ONGOING' && this.game.status !== 'IN_PROGRESS'
    },

    statusLabel() {
      if (!this.game) return 'En attente'

      const labels = {
        ONGOING: 'Bataille en cours',
        IN_PROGRESS: 'Bataille en cours',
        FINISHED: 'Bataille terminée',
        DRAW: 'Aucun vainqueur'
      }

      return labels[this.game.status] || this.game.status
    },

    currentFactionLabel() {
      return this.currentSymbol === 'X' ? 'Imperium' : 'Chaos'
    }
  },

  methods: {
    cellClass(cell) {
      return {
        'cell-imperium': cell === 'X',
        'cell-chaos': cell === 'O'
      }
    },

    async createGame() {
      this.loading = true
      this.message = ''

      try {
        const response = await axios.post(`${API_URL}/games`, {
          gameType: 'tictactoe',
          playerCount: 2,
          boardSize: 3
        })

        this.game = response.data
        this.cells = Array((this.game.boardSize || 3) * (this.game.boardSize || 3)).fill('')
        this.currentSymbol = 'X'
        this.message = "Archive ouverte. L'Imperium joue le premier coup."
      } catch (error) {
        this.message = "Impossible d'ouvrir la partie. Vérifie que l'API tourne sur localhost:8080."
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async play(index) {
      if (!this.game || this.cells[index] !== '') return

      const boardSize = this.game.boardSize || 3
      const x = index % boardSize
      const y = Math.floor(index / boardSize)
      const playedSymbol = this.currentSymbol

      this.loading = true
      this.message = ''

      try {
        const response = await axios.post(`${API_URL}/games/${this.game.gameId}/moves`, {
          playerId: this.game.currentPlayerId,
          tokenId: playedSymbol,
          targetx: x,
          targety: y
        })

        if (String(response.data).toLowerCase().includes('invalide')) {
          this.message = response.data
          return
        }

        this.cells[index] = playedSymbol
        this.currentSymbol = playedSymbol === 'X' ? 'O' : 'X'
        await this.refreshGame()
        this.message = response.data
      } catch (error) {
        this.message = 'Le vox a perdu le signal avec le serveur.'
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    async refreshGame() {
      const response = await axios.get(`${API_URL}/games/${this.game.gameId}`)
      this.game = response.data
    }
  }
}
</script>
