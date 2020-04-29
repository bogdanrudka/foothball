import Vue from 'vue'
import Router from 'vue-router'
import ScoreGrid from "../components/ScoreGrid";
import PlayersGrid from "../components/PlayersGrid";
import Games from "../components/Games";

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/scores',
            name: 'Scores',
            component: ScoreGrid
        },
        {
            path: '/players',
            name: 'Players',
            component: PlayersGrid
        },
        {
            path: '/games',
            name: 'Games',
            component: Games
        },
    ]
})