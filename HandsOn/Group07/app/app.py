# -*- coding: utf-8 -*-
"""nuestra

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/19klN-ttLPeASpGPFKhYb1gJOvBMQC-RB
"""

!pip install rdflib

from rdflib import Graph, Namespace
from rdflib.plugins.sparql import prepareQuery

districtsList = ["ARGANZUELA", "BARAJAS", "CARABANCHEL", "CENTRO", "CHAMARTIN", 
                 "CHAMBERI", "CIUDAD-LINEAL", "FUENCARRAL-EL-PARDO", "HORTALEZA", 
                 "LATINA", "MONCLOA-ARAVACA", "MORATALAZ", "PUENTE-DE-VALLECAS", 
                 "RETIRO", "SALAMANCA", "SAN-BLAS", "TETUAN", "USERA", "VICALVARO", 
                 "VILLA-DE-VALLECAS", "VILLAVERDE"]

print('''DISTRICTS''')

print ('''
 1.ARGANZUELA
 2.BARAJAS
 3.CARABANCHEL
 4.CENTRO
 5.CHAMARTIN
 6.CHAMBERI
 7.CIUDAD-LINEAL
 8.FUENCARRAL-EL-PARDO
 9.HORTALEZA
10.LATINA
11.MONCLOA-ARAVACA
12.MORATALAZ
13.PUENTE-DE-VALLECAS 
14.RETIRO
15.SALAMANCA
16.SAN-BLAS
17.TETUAN
18.USERA
19.VICALVARO
20.VILLA-DE-VALLECAS
21.VILLAVERDE''')
ans=input('''Which district do you want to check fountains from? ''')
if ans=="1": 
  selectDistrict = districtsList.index("ARGANZUELA")
elif ans=="2":
  selectDistrict = districtsList.index("BARAJAS")
elif ans=="3":
  selectDistrict = districtsList.index("CARABANCHEL")
elif ans=="4": 
  selectDistrict = districtsList.index("CENTRO")
elif ans=="5": 
  selectDistrict = districtsList.index("CHAMARTIN")
elif ans=="6": 
  selectDistrict = districtsList.index("CHAMBERI")
elif ans=="7": 
  selectDistrict = districtsList.index("CIUDAD-LINEAL")
elif ans=="8": 
  selectDistrict = districtsList.index("FUENCARRAL-EL-PARDO")
elif ans=="9": 
  selectDistrict = districtsList.index("HORTALEZA")
elif ans=="10": 
  selectDistrict = districtsList.index("LATINA")
elif ans=="11": 
  selectDistrict = districtsList.index("MONCLOA-ARAVACA")
elif ans=="12": 
  selectDistrict = districtsList.index("MORATALAZ")
elif ans=="13": 
  selectDistrict = districtsList.index("PUENTE-DE-VALLECAS")
elif ans=="14":
  selectDistrict = districtsList.index("RETIRO")
elif ans=="15":
  selectDistrict = districtsList.index("SALAMANCA")
elif ans=="16":
  selectDistrict = districtsList.index("SAN-BLAS")
elif ans=="17":
  selectDistrict = districtsList.index("TETUAN")
elif ans=="18":
  selectDistrict = districtsList.index("USERA")
elif ans=="19":
  selectDistrict = districtsList.index("VICALVARO")
elif ans=="20":
  selectDistrict = districtsList.index("VILLA-DE-VALLECAS")
elif ans=="21":
  selectDistrict = districtsList.index("VILLAVERDE")

path = 'output.nt'
g = Graph()
g.namespace_manager.bind('fountain', Namespace("http://www.smartCityParks.es/group07/resource/Fountain/"), override=False)

fountain = Namespace("http://www.smartCityParks.es/group07/resource/Fountain/")
g.parse(path, format="turtle")


q1 = '''SELECT ?a ?b ?c
WHERE {
 ?f <http://www.smartCityParks.es/group07/ontology/Font#isInDistrict> 
 <http://www.smartCityParks.es/group07/resource/District/''' + districtsList[selectDistrict] + '''>.
 ?f ?a ?b.
 <http://www.smartCityParks.es/group07/resource/District/''' +districtsList[selectDistrict] + '''> <http://www.smartCityParks.es/group07/ontology/Dist#hasNumInhabitants> ?c

}'''

print("---------------------------------------------------------------------")
i=1
control=0
for s in g.query(q1):
  #print("...",s.a, s.b)
  if(i==1):
    print("FOUNTAIN", i)
    i=i+1
  if(s.a[:63] == "http://www.smartCityParks.es/group07/ontology/Font#isInDistrict"):
    if(s.b[:55]=="http://www.smartCityParks.es/group07/resource/District/"):
      print("DISTRICT:", s.b[55:])
      print("Number of inhabitants:", s.c)
      print("\nFOUNTAIN", i)
      i=i+1
      control=0

  else: 
    if(control==0):
          control=1
    else:
          print(s.a[51:], s.b)