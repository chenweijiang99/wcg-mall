<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { ref, computed, onMounted } from "vue";
//分页条数据模型
const pageNum = ref(1); //当前页
const pageSize = ref(5); //每页条数
// 当前页码

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
};

const users = ref([]);

const displayUsers = computed(() => {
  if (users.value != null) {
    const startIndex = (pageNum.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    return users.value.slice(startIndex, endIndex);
  }else {
    return users.value=[];
  }
});

import {
  getUserListService,
  resetUserPasswordService,
  deleteUserService,
} from "@/api/user.js";

//获取用户列表
const getUserList = async () => {
  const result = await getUserListService();
  users.value = result.data;
};
getUserList();
//重置用户密码
const resetPassword = async (id) => {
  const result = await resetUserPasswordService(id);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "重置密码成功");
  } else {
    ElMessage.error(result.msg ? result.msg : "重置密码失败");
  }
};

//删除用户
const deleteUser = async (id) => {
  ElMessageBox.confirm("确定要删除该用户吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(async () => {
    const result = await deleteUserService(id);
    if (result.code === 1) {
      ElMessage.success(result.msg ? result.msg : "删除用户成功");
      getUserList();
    } else {
      ElMessage.error(result.msg ? result.msg : "删除用户失败");
    }
  });
};

</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>用户管理</span>
      </div>
    </template>

    <!-- 用户列表 -->
    <el-table :data="displayUsers">
      <el-table-column label="ID" prop="id" width="100"></el-table-column>
      <el-table-column
        label="姓名"
        prop="username"
        width="100"
      ></el-table-column>
      <el-table-column label="昵称" prop="nickName" width="200">
      </el-table-column>
      <el-table-column label="邮箱" prop="email" width="300"></el-table-column>
      <el-table-column label="电话" prop="phone" width="200"></el-table-column>
      <el-table-column label="角色" prop="status">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.status == 1
                ? 'success'
                : scope.row.status == 2
                ? 'primary'
                : 'danger'
            "
            disable-transitions
          >
            {{
              scope.row.status === 1
                ? "普通用户"
                : scope.row.status === 2
                ? "买家"
                : "管理员"
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="state">
        <template #default="scope">
          {{ scope.row.state === 1 ? "已激活" : "待激活" }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250px">
        <template #default="scope">
          <el-button
            plain
            type="info"
            size="small"
            @click="resetPassword(scope.row.id)"
            >重置密码</el-button
          >
          <el-button
            plain
            type="danger"
            size="small"
            @click="deleteUser(scope.row.id)"
            >删除用户</el-button
          >
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
    v-if="users.length > 0"
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      layout=" total, prev, pager, next"
      background
      :total="users.length"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />
  </el-card>
</template>
<style lang="scss" scoped>
.hidden {
  display: none;
}
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .el-select {
    width: 150px;
  }
}
</style>