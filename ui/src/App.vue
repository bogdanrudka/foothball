<template>
    <div id="app">
        <b-navbar toggleable="lg" type="dark" variant="info">
            <b-navbar-brand :to="{name: 'Scores'}">Scores</b-navbar-brand>
            <b-navbar-brand :to="{name: 'Players'}">Players</b-navbar-brand>
            <b-navbar-brand :to="{name: 'Games'}">Games</b-navbar-brand>
        </b-navbar>
        <router-view></router-view>
    </div>
</template>

<script>
    import 'bootstrap/dist/css/bootstrap.css'
    import 'bootstrap-vue/dist/bootstrap-vue.css'

    export default {
        name: 'App'
    }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }
</style>
