// import './assets/main.css'

import './assets/init.css'
import { createApp } from 'vue'
import App from './components/body.vue'
import router from './router.js'

const app = createApp(App);
app.use(router);
app.mount('#app')
