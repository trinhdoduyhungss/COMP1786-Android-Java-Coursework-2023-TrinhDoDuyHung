import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import React, { useEffect } from "react";

import Database from "./Database/Database";
import HomeScreen from "./screens/HomeScreen";
import AddScreen from "./screens/AddScreen";
import DetailScreen from "./screens/DetailScreen";

const Stack = createStackNavigator();

const App = () => {
  useEffect(() => {
    Database.initDatabase();
  }, []);

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Add new hike" component={AddScreen} />
        <Stack.Screen name="Edit hike" component={DetailScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
