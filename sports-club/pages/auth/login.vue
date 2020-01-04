<!-- eslint-disable -->
<template>
  <b-container>
    <h3>Bem-Vindo ao Sports Club</h3>
    <b-form @submit.prevent="onSubmit" @reset="onReset">
      <b-form-group label="Username" description="Enter your username">
        <b-input name="username" placeholder="Your username" v-model.trim="username" required />
      </b-form-group>
      <b-form-group label="Password" description="Enter your password">
        <b-input
          name="password"
          type="password"
          placeholder="Your password"
          v-model="password"
          required
        />
      </b-form-group>
      <b-button type="reset" class="btn-warning">Reset</b-button>
      <b-button type="Submit" class="btn-success">Submit</b-button>
    </b-form>
  </b-container>
</template>

<script>
/* eslint-disable */
export default {
  auth: false,
  data() {
    return {
      username: null,
      password: null
    };
  },
  methods: {
    onSubmit() {
      let promise = this.$auth.loginWith("local", {
        data: {
          username: this.username,
          password: this.password
        }
      });
      promise.then(() => {
        this.$toast.success("Bem-vindo!");
        console.log(this.$auth.user)
        if (this.$auth.user.groups.includes("Administrador")) {
          this.$router.push(`/`);
        } else if (this.$auth.user.groups.includes("Atleta")) {
          this.$router.push(`/${this.username}/atleta`);
        } else if (this.$auth.user.groups.includes("Treinador")) {
          this.$router.push(`/${this.username}/treinador`);
        } else if (this.$auth.user.groups.includes("Socio")) {
          this.$router.push(`/${this.username}/socio`);
        }
      });
      promise.catch(() => {
        this.$toast.error(
          "Credênciais inválidas!"
        );
      });
    },
    onReset() {
      this.username = null;
      this.password = null;
    }
  }
};
</script>
