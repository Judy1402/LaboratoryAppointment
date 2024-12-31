import { createRouter, createWebHashHistory } from "vue-router";
import Home from "./components/Home.vue";
import Table from "./components/Table.vue";
import User from "./components/User.vue";
import Manage from "./components/Manage.vue";

const router = createRouter(
    {
        history: createWebHashHistory(),
        routes: [
            {path: "/", component: Home},
            {path: "/start", component: Home},
            {path: "/Home", component: Home},
            {path: "/Table", component: Table},
            {path: "/User", component: User},
            {path: "/Manage", component: Manage}
        ]
    }
) 
export default router;