<template>
  <div class="content">
    <div class="body">
      <div class="sidebar">
        <Sidebar></Sidebar>
      </div>
      <div class="view">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import "../assets/init.css";
import Sidebar from "@/components/Sidebar.vue";
import { ref, onMounted } from "vue";

const box = ref(null);

// onMounted(() => {
//   if (box.value) {
//     box.value.showModal();
//   }
// });
</script>

<style scoped>

.content {
  /* display: none; */
  background-color: aliceblue;
}
.body {
  display: flex;
  width: 100%;
}
.view{
  /* 占用剩余空间 */
  flex: 1;
  padding: 10px;
}
</style>
