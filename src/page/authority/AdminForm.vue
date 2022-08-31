<template>
  <div>
    <el-form :model="admin" ref="adminForm" label-width="150px">
      <el-form-item label="帐号：">
        <el-input v-model="admin.username" style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item label="昵称：">
        <el-input v-model="admin.nickName" style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item label="头像：">
        <el-input
          v-model="admin.avatar"
          style="width: 250px"
          placeholder="请填写图片URL"
        ></el-input>
      </el-form-item>
      <el-form-item label="邮箱：">
        <el-input v-model="admin.mail" style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item label="密码：">
        <el-input
          v-model="admin.password"
          type="password"
          style="width: 250px"
        ></el-input>
      </el-form-item>
      <el-form-item label="是否启用：">
        <el-radio-group v-model="admin.enable">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="分配角色" v-if="admin.id">
        <el-checkbox-group v-model="selectedRoles">
          <el-checkbox
            :label="item.id"
            v-for="item in roleList"
            :key="item.id"
            >{{ item.name }}</el-checkbox
          >
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {
  getAdmin,
  alloc_role,
  roleList,
  getRoleByAdmin,
  createAdmin,
  updateAdmin,
} from "../../api/authority";

export default {
  data() {
    return {
      admin: {},
      roleList: [],
      selectedRoles: [],
    };
  },
  methods: {
    fetchData(id) {
      getAdmin(id).then((res) => {
        this.admin = res;
        getRoleByAdmin(this.admin.id).then(({ arrays }) => {
          this.selectedRoles = arrays.map((item) => item.id);
        });
      });
    },
    onSubmit() {
      if (this.admin.id) {
        updateAdmin(this.admin).then(() =>
          this.$message.success("修改管理员信息成功")
        );
        alloc_role({
          userId: this.admin.id,
          roleIds: this.selectedRoles,
        }).then(() => this.$message.success("更新权限角色成功"));
      } else {
        createAdmin(this.admin).then(() =>
          this.$message.success("创建管理员信息成功")
        );
      }
    },
  },
  created() {
    if (this.$route.query.username) {
      this.fetchData(this.$route.query.username);
    }
    roleList().then(({ arrays }) => (this.roleList = arrays));
  },
};
</script>

<style></style>
