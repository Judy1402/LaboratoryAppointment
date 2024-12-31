<template>
  <section ref="sidebar">
    <div class="logo">
      <img src="../assets/picture/t01998e27e32cc4eb88.jpg" alt="校徽">
      <h2 v-show="isStretch">教室预约系统</h2>
    </div>
    <div class = "stretch">
      <div v-show="isStretch">{{ welcome }}</div>
      <img src="../assets/svg/左边.svg" alt="左边" ref = "left" @click = "stretch">
      <img src="../assets/svg/右边.svg" alt="右边" ref = "right" @click = "stretch">
    </div>
    <hr>
    <ul>
      <li v-for="(item, index) in list " :key = "index">
        <div @click = "clickSidebar(index)" :class="{afterClick: index === active}">
          <router-link :to="item.path">
            <img :src="item.src" alt="图标" :title="item.title">
            <h3 v-show = "isStretch">{{ item.name }}</h3>
          </router-link>
        </div>
      </li> 
    </ul>
  </section>
  <div ref="empty" style="transition: 1s all; width: 250px;"></div>
</template>

<style scoped>
  section {
    padding: 10px;
    width: 250px;
    height: 100vh;
    transition: 1s all;
    background-color: ghostwhite;
    position: fixed;
    z-index: 1000;
  }
  section img{
    display: block;
    width: 50px;
  }
  .logo{
    height: 70px;
    overflow: hidden;
    padding: 10px;
    display: flex;
  }
  .logo img{
    border-radius: 50%;
  }
  .logo h2{
    padding-left: 10px;
    text-align: center;
    line-height: 50px;
  }
  .stretch{
    height: 70px;
    overflow: hidden;
    padding: 10px;
    display: flex;
  }
  .stretch div{
    padding-left: 10px;
    text-align: center;
    line-height: 50px;
  }
  .stretch img{
    cursor: pointer;
    margin-left: auto;
    opacity: 0.5;
  }
  .stretch img:hover{
    opacity: 1;
  }
  .stretch img[alt = "右边"]{
    display: none;
  }
  ul{
    list-style: none;
    padding: 0;
  }
  ul li div{
    height: 60px;
    overflow: hidden;
    border-radius: 10px;
    padding: 5px 10px;
    margin: 10px 0;
    display: flex;
  }
  ul li div:hover{
    box-shadow: 0 0 5px white;
    background-color: rgb(232, 246, 250);

  }
  ul li div h3{
    padding-left: 20px;
    text-align: center;
    line-height: 50px;
  }
  ul li div:hover h3{
    color: rgb(119, 132, 250);
  }
  .afterClick{
    background: linear-gradient(45deg, rgb(119, 132, 250), rgb(214, 159, 239));
  }
  .afterClick h3{
    color: white;
  }
  router-link, ul li div a{
    width: 100%;
    display: flex;
  }

  
</style>

<script setup>
  import { ref } from 'vue';
  const name = ref('USER');

  const sidebar = ref(null);
  const left = ref(null);
  const right = ref(null);

  const list = [
    {name: "首 页", src: "/src/assets/svg/首页.svg", path: "/Home", title: "首页"},
    {name: "课 表", src: "/src/assets/svg/表格.svg", path: "/Table", title: "课表"},
    {name: "管 理", src: "/src/assets/svg/管理.svg", path: "/Manage", title: "管理"},
    {name: "个 人", src: "/src/assets/svg/用户.svg", path: "/User", title: "个人"},
  ];

  const isStretch = ref(true);
  const empty = ref(null);
  const stretch = ( e ) => {
    if(e.target.alt === "左边"){
      left.value.style.display = "none";
      right.value.style.display = "block";
      isStretch.value = false;
      sidebar.value.style.width = "90px";
      empty.value.style.width = "90px";
    }else{
      left.value.style.display = "block";
      right.value.style.display = "none";
      sidebar.value.style.width = "250px";
      empty.value.style.width = "250px";
      isStretch.value = true;
    }
  }

  const active = ref(-1);
  const clickSidebar = ( index ) => {
    active.value = index;
  }

  const welcome = ref("未登录");
  let user = localStorage.getItem("user");
  if(user !== null){
    user = JSON.parse(user);
    welcome.value = `欢迎您，${user.name}`;
  }
  

</script>
