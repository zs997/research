package tspbenchmark;

public class CityInstance {
    int numCities;
    City cities[];
    public CityInstance(){
        numCities = 31;
        cities = new City[31];
        cities[0] = new City(1304,2312);
        cities[1] = new City(3639,1315);
        cities[2] = new City(4177,2244);
        cities[3] = new City(3712,1399);
        cities[4] = new City(3488,1535);
        cities[5] = new City(3326,1556);
        cities[6] = new City(3238,1229);
        cities[7] = new City(4196,1004);
        cities[8] = new City(4312,790);
        cities[9] = new City(4386,570);
        cities[10] = new City(3007,1970);
        cities[11] = new City(2562,1756);
        cities[12] = new City(2788,1491);
        cities[13] = new City(2381,1676);
        cities[14] = new City(1332,695);
        cities[15] = new City(3715,1678);
        cities[16] = new City(3918,2179);
        cities[17] = new City(4061,2370);
        cities[18] = new City(3780,2212);
        cities[19] = new City(3676,2578);
        cities[20] = new City(4029,2838);
        cities[21] = new City(4263,2931);
        cities[22] = new City(3429,1908);
        cities[23] = new City(3507,2367);
        cities[24] = new City(3394,2643);
        cities[25] = new City(3439,3201);
        cities[26] = new City(2935,3240);
        cities[27] = new City(3140,3550);
        cities[28] = new City(2545,2357);
        cities[29] = new City(2778,2826);
        cities[30] = new City(2370,2975);

//        numCities = 5;
//        cities = new City[5];
//        cities[0] = new City(1304,2312);
//        cities[1] = new City(3639,1315);
//        cities[2] = new City(4177,2244);
//        cities[3] = new City(3712,1399);
//        cities[4] = new City(3488,1535);

    }

    public int getNumCities() {
        return numCities;
    }


    public City[] getCities() {
        return cities;
    }


}
