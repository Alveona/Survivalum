package
{
	import Box2D.Dynamics.*;
	import Box2D.Collision.*;
	import Box2D.Collision.Shapes.*;
	import Box2D.Common.Math.*;
    import flash.utils.Dictionary;

    public class PhysicsData extends Object
	{
		// ptm ratio
        public var ptm_ratio:Number = 32;
		
		// the physcis data 
		var dict:Dictionary;
		
        //
        // bodytype:
        //  b2_staticBody
        //  b2_kinematicBody
        //  b2_dynamicBody

        public function createBody(name:String, world:b2World, bodyType:uint, userData:*):b2Body
        {
            var fixtures:Array = dict[name];

            var body:b2Body;
            var f:Number;

            // prepare body def
            var bodyDef:b2BodyDef = new b2BodyDef();
            bodyDef.type = bodyType;
            bodyDef.userData = userData;

            // create the body
            body = world.CreateBody(bodyDef);

            // prepare fixtures
            for(f=0; f<fixtures.length; f++)
            {
                var fixture:Array = fixtures[f];

                var fixtureDef:b2FixtureDef = new b2FixtureDef();

                fixtureDef.density=fixture[0];
                fixtureDef.friction=fixture[1];
                fixtureDef.restitution=fixture[2];

                fixtureDef.filter.categoryBits = fixture[3];
                fixtureDef.filter.maskBits = fixture[4];
                fixtureDef.filter.groupIndex = fixture[5];
                fixtureDef.isSensor = fixture[6];

                if(fixture[7] == "POLYGON")
                {                    
                    var p:Number;
                    var polygons:Array = fixture[8];
                    for(p=0; p<polygons.length; p++)
                    {
                        var polygonShape:b2PolygonShape = new b2PolygonShape();
                        polygonShape.SetAsArray(polygons[p], polygons[p].length);
                        fixtureDef.shape=polygonShape;

                        body.CreateFixture(fixtureDef);
                    }
                }
                else if(fixture[7] == "CIRCLE")
                {
                    var circleShape:b2CircleShape = new b2CircleShape(fixture[9]);                    
                    circleShape.SetLocalPosition(fixture[8]);
                    fixtureDef.shape=circleShape;
                    body.CreateFixture(fixtureDef);                    
                }                
            }

            return body;
        }

		
        public function PhysicsData(): void
		{
			dict = new Dictionary();
			

			dict["hero"] = [

										[
											// density, friction, restitution
                                            2, 0, 0,
                                            // categoryBits, maskBits, groupIndex, isSensor
											1, 65535, 0, false,
											'POLYGON',

                                            // vertexes of decomposed polygons
                                            [

                                                [   new b2Vec2(48/ptm_ratio, 63/ptm_ratio)  ,  new b2Vec2(47/ptm_ratio, 65/ptm_ratio)  ,  new b2Vec2(46/ptm_ratio, 63/ptm_ratio)  ] ,
                                                [   new b2Vec2(245/ptm_ratio, 169/ptm_ratio)  ,  new b2Vec2(238/ptm_ratio, 167/ptm_ratio)  ,  new b2Vec2(241/ptm_ratio, 160/ptm_ratio)  ] ,
                                                [   new b2Vec2(50/ptm_ratio, 61/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 63/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 61/ptm_ratio)  ] ,
                                                [   new b2Vec2(131/ptm_ratio, 49/ptm_ratio)  ,  new b2Vec2(129/ptm_ratio, 47/ptm_ratio)  ,  new b2Vec2(131/ptm_ratio, 47/ptm_ratio)  ] ,
                                                [   new b2Vec2(138/ptm_ratio, 68/ptm_ratio)  ,  new b2Vec2(53/ptm_ratio, 58/ptm_ratio)  ,  new b2Vec2(55/ptm_ratio, 55/ptm_ratio)  ,  new b2Vec2(115/ptm_ratio, 45/ptm_ratio)  ,  new b2Vec2(131/ptm_ratio, 49/ptm_ratio)  ,  new b2Vec2(134/ptm_ratio, 52/ptm_ratio)  ,  new b2Vec2(140/ptm_ratio, 66/ptm_ratio)  ,  new b2Vec2(140/ptm_ratio, 68/ptm_ratio)  ] ,
                                                [   new b2Vec2(223/ptm_ratio, 123/ptm_ratio)  ,  new b2Vec2(222/ptm_ratio, 120/ptm_ratio)  ,  new b2Vec2(224/ptm_ratio, 121/ptm_ratio)  ] ,
                                                [   new b2Vec2(213/ptm_ratio, 103/ptm_ratio)  ,  new b2Vec2(211/ptm_ratio, 100/ptm_ratio)  ,  new b2Vec2(213/ptm_ratio, 100/ptm_ratio)  ] ,
                                                [   new b2Vec2(185/ptm_ratio, 79/ptm_ratio)  ,  new b2Vec2(181/ptm_ratio, 77/ptm_ratio)  ,  new b2Vec2(183/ptm_ratio, 76/ptm_ratio)  ] ,
                                                [   new b2Vec2(140/ptm_ratio, 66/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 58/ptm_ratio)  ,  new b2Vec2(142/ptm_ratio, 59/ptm_ratio)  ,  new b2Vec2(143/ptm_ratio, 63/ptm_ratio)  ] ,
                                                [   new b2Vec2(115/ptm_ratio, 45/ptm_ratio)  ,  new b2Vec2(55/ptm_ratio, 55/ptm_ratio)  ,  new b2Vec2(105/ptm_ratio, 40/ptm_ratio)  ,  new b2Vec2(113/ptm_ratio, 40/ptm_ratio)  ] ,
                                                [   new b2Vec2(44/ptm_ratio, 126/ptm_ratio)  ,  new b2Vec2(41/ptm_ratio, 132/ptm_ratio)  ,  new b2Vec2(41/ptm_ratio, 128/ptm_ratio)  ] ,
                                                [   new b2Vec2(53/ptm_ratio, 58/ptm_ratio)  ,  new b2Vec2(138/ptm_ratio, 68/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 120/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 117/ptm_ratio)  ,  new b2Vec2(50/ptm_ratio, 61/ptm_ratio)  ,  new b2Vec2(51/ptm_ratio, 58/ptm_ratio)  ] ,
                                                [   new b2Vec2(134/ptm_ratio, 52/ptm_ratio)  ,  new b2Vec2(131/ptm_ratio, 49/ptm_ratio)  ,  new b2Vec2(133/ptm_ratio, 49/ptm_ratio)  ] ,
                                                [   new b2Vec2(219/ptm_ratio, 114/ptm_ratio)  ,  new b2Vec2(217/ptm_ratio, 110/ptm_ratio)  ,  new b2Vec2(219/ptm_ratio, 111/ptm_ratio)  ] ,
                                                [   new b2Vec2(44/ptm_ratio, 73/ptm_ratio)  ,  new b2Vec2(42/ptm_ratio, 84/ptm_ratio)  ,  new b2Vec2(40/ptm_ratio, 77/ptm_ratio)  ] ,
                                                [   new b2Vec2(112/ptm_ratio, 187/ptm_ratio)  ,  new b2Vec2(118/ptm_ratio, 186/ptm_ratio)  ,  new b2Vec2(115/ptm_ratio, 188/ptm_ratio)  ] ,
                                                [   new b2Vec2(42/ptm_ratio, 84/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 117/ptm_ratio)  ,  new b2Vec2(40/ptm_ratio, 114/ptm_ratio)  ,  new b2Vec2(36/ptm_ratio, 98/ptm_ratio)  ,  new b2Vec2(36/ptm_ratio, 89/ptm_ratio)  ] ,
                                                [   new b2Vec2(208/ptm_ratio, 162/ptm_ratio)  ,  new b2Vec2(238/ptm_ratio, 163/ptm_ratio)  ,  new b2Vec2(219/ptm_ratio, 170/ptm_ratio)  ,  new b2Vec2(211/ptm_ratio, 168/ptm_ratio)  ] ,
                                                [   new b2Vec2(133/ptm_ratio, 181/ptm_ratio)  ,  new b2Vec2(39/ptm_ratio, 135/ptm_ratio)  ,  new b2Vec2(138/ptm_ratio, 179/ptm_ratio)  ,  new b2Vec2(137/ptm_ratio, 181/ptm_ratio)  ] ,
                                                [   new b2Vec2(63/ptm_ratio, 184/ptm_ratio)  ,  new b2Vec2(47/ptm_ratio, 171/ptm_ratio)  ,  new b2Vec2(70/ptm_ratio, 186/ptm_ratio)  ,  new b2Vec2(67/ptm_ratio, 187/ptm_ratio)  ] ,
                                                [   new b2Vec2(40/ptm_ratio, 114/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 117/ptm_ratio)  ,  new b2Vec2(42/ptm_ratio, 117/ptm_ratio)  ] ,
                                                [   new b2Vec2(194/ptm_ratio, 84/ptm_ratio)  ,  new b2Vec2(185/ptm_ratio, 79/ptm_ratio)  ,  new b2Vec2(191/ptm_ratio, 80/ptm_ratio)  ] ,
                                                [   new b2Vec2(48/ptm_ratio, 120/ptm_ratio)  ,  new b2Vec2(44/ptm_ratio, 126/ptm_ratio)  ,  new b2Vec2(44/ptm_ratio, 123/ptm_ratio)  ] ,
                                                [   new b2Vec2(217/ptm_ratio, 110/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(213/ptm_ratio, 103/ptm_ratio)  ,  new b2Vec2(216/ptm_ratio, 105/ptm_ratio)  ] ,
                                                [   new b2Vec2(127/ptm_ratio, 183/ptm_ratio)  ,  new b2Vec2(39/ptm_ratio, 135/ptm_ratio)  ,  new b2Vec2(133/ptm_ratio, 181/ptm_ratio)  ,  new b2Vec2(131/ptm_ratio, 183/ptm_ratio)  ] ,
                                                [   new b2Vec2(55/ptm_ratio, 180/ptm_ratio)  ,  new b2Vec2(47/ptm_ratio, 171/ptm_ratio)  ,  new b2Vec2(63/ptm_ratio, 184/ptm_ratio)  ,  new b2Vec2(59/ptm_ratio, 184/ptm_ratio)  ] ,
                                                [   new b2Vec2(181/ptm_ratio, 77/ptm_ratio)  ,  new b2Vec2(175/ptm_ratio, 75/ptm_ratio)  ,  new b2Vec2(177/ptm_ratio, 74/ptm_ratio)  ] ,
                                                [   new b2Vec2(219/ptm_ratio, 170/ptm_ratio)  ,  new b2Vec2(238/ptm_ratio, 163/ptm_ratio)  ,  new b2Vec2(235/ptm_ratio, 169/ptm_ratio)  ,  new b2Vec2(225/ptm_ratio, 172/ptm_ratio)  ,  new b2Vec2(220/ptm_ratio, 172/ptm_ratio)  ] ,
                                                [   new b2Vec2(211/ptm_ratio, 100/ptm_ratio)  ,  new b2Vec2(207/ptm_ratio, 95/ptm_ratio)  ,  new b2Vec2(210/ptm_ratio, 97/ptm_ratio)  ] ,
                                                [   new b2Vec2(105/ptm_ratio, 40/ptm_ratio)  ,  new b2Vec2(55/ptm_ratio, 55/ptm_ratio)  ,  new b2Vec2(85/ptm_ratio, 39/ptm_ratio)  ,  new b2Vec2(100/ptm_ratio, 37/ptm_ratio)  ,  new b2Vec2(104/ptm_ratio, 38/ptm_ratio)  ] ,
                                                [   new b2Vec2(47/ptm_ratio, 65/ptm_ratio)  ,  new b2Vec2(44/ptm_ratio, 73/ptm_ratio)  ,  new b2Vec2(43/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(45/ptm_ratio, 65/ptm_ratio)  ] ,
                                                [   new b2Vec2(207/ptm_ratio, 95/ptm_ratio)  ,  new b2Vec2(202/ptm_ratio, 91/ptm_ratio)  ,  new b2Vec2(207/ptm_ratio, 93/ptm_ratio)  ] ,
                                                [   new b2Vec2(47/ptm_ratio, 171/ptm_ratio)  ,  new b2Vec2(55/ptm_ratio, 180/ptm_ratio)  ,  new b2Vec2(52/ptm_ratio, 180/ptm_ratio)  ,  new b2Vec2(47/ptm_ratio, 175/ptm_ratio)  ] ,
                                                [   new b2Vec2(125/ptm_ratio, 43/ptm_ratio)  ,  new b2Vec2(131/ptm_ratio, 49/ptm_ratio)  ,  new b2Vec2(115/ptm_ratio, 45/ptm_ratio)  ,  new b2Vec2(121/ptm_ratio, 42/ptm_ratio)  ] ,
                                                [   new b2Vec2(222/ptm_ratio, 120/ptm_ratio)  ,  new b2Vec2(219/ptm_ratio, 114/ptm_ratio)  ,  new b2Vec2(221/ptm_ratio, 115/ptm_ratio)  ] ,
                                                [   new b2Vec2(202/ptm_ratio, 91/ptm_ratio)  ,  new b2Vec2(194/ptm_ratio, 84/ptm_ratio)  ,  new b2Vec2(200/ptm_ratio, 87/ptm_ratio)  ] ,
                                                [   new b2Vec2(266/ptm_ratio, 130/ptm_ratio)  ,  new b2Vec2(301/ptm_ratio, 146/ptm_ratio)  ,  new b2Vec2(297/ptm_ratio, 154/ptm_ratio)  ,  new b2Vec2(295/ptm_ratio, 156/ptm_ratio)  ,  new b2Vec2(245/ptm_ratio, 169/ptm_ratio)  ,  new b2Vec2(238/ptm_ratio, 163/ptm_ratio)  ,  new b2Vec2(235/ptm_ratio, 138/ptm_ratio)  ,  new b2Vec2(245/ptm_ratio, 130/ptm_ratio)  ] ,
                                                [   new b2Vec2(139/ptm_ratio, 58/ptm_ratio)  ,  new b2Vec2(140/ptm_ratio, 66/ptm_ratio)  ,  new b2Vec2(134/ptm_ratio, 52/ptm_ratio)  ,  new b2Vec2(136/ptm_ratio, 52/ptm_ratio)  ] ,
                                                [   new b2Vec2(163/ptm_ratio, 74/ptm_ratio)  ,  new b2Vec2(154/ptm_ratio, 71/ptm_ratio)  ,  new b2Vec2(158/ptm_ratio, 71/ptm_ratio)  ] ,
                                                [   new b2Vec2(153/ptm_ratio, 172/ptm_ratio)  ,  new b2Vec2(39/ptm_ratio, 135/ptm_ratio)  ,  new b2Vec2(181/ptm_ratio, 162/ptm_ratio)  ,  new b2Vec2(177/ptm_ratio, 166/ptm_ratio)  ,  new b2Vec2(165/ptm_ratio, 172/ptm_ratio)  ] ,
                                                [   new b2Vec2(39/ptm_ratio, 135/ptm_ratio)  ,  new b2Vec2(127/ptm_ratio, 183/ptm_ratio)  ,  new b2Vec2(112/ptm_ratio, 187/ptm_ratio)  ,  new b2Vec2(82/ptm_ratio, 188/ptm_ratio)  ,  new b2Vec2(70/ptm_ratio, 186/ptm_ratio)  ,  new b2Vec2(47/ptm_ratio, 171/ptm_ratio)  ,  new b2Vec2(43/ptm_ratio, 168/ptm_ratio)  ,  new b2Vec2(37/ptm_ratio, 157/ptm_ratio)  ] ,
                                                [   new b2Vec2(175/ptm_ratio, 75/ptm_ratio)  ,  new b2Vec2(163/ptm_ratio, 74/ptm_ratio)  ,  new b2Vec2(172/ptm_ratio, 73/ptm_ratio)  ] ,
                                                [   new b2Vec2(229/ptm_ratio, 136/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(223/ptm_ratio, 123/ptm_ratio)  ,  new b2Vec2(227/ptm_ratio, 128/ptm_ratio)  ] ,
                                                [   new b2Vec2(118/ptm_ratio, 186/ptm_ratio)  ,  new b2Vec2(127/ptm_ratio, 183/ptm_ratio)  ,  new b2Vec2(125/ptm_ratio, 185/ptm_ratio)  ] ,
                                                [   new b2Vec2(70/ptm_ratio, 186/ptm_ratio)  ,  new b2Vec2(82/ptm_ratio, 188/ptm_ratio)  ,  new b2Vec2(74/ptm_ratio, 188/ptm_ratio)  ] ,
                                                [   new b2Vec2(138/ptm_ratio, 179/ptm_ratio)  ,  new b2Vec2(39/ptm_ratio, 135/ptm_ratio)  ,  new b2Vec2(153/ptm_ratio, 172/ptm_ratio)  ,  new b2Vec2(144/ptm_ratio, 178/ptm_ratio)  ] ,
                                                [   new b2Vec2(50/ptm_ratio, 61/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 117/ptm_ratio)  ,  new b2Vec2(42/ptm_ratio, 84/ptm_ratio)  ,  new b2Vec2(44/ptm_ratio, 73/ptm_ratio)  ,  new b2Vec2(47/ptm_ratio, 65/ptm_ratio)  ,  new b2Vec2(48/ptm_ratio, 63/ptm_ratio)  ] ,
                                                [   new b2Vec2(154/ptm_ratio, 71/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(153/ptm_ratio, 69/ptm_ratio)  ] ,
                                                [   new b2Vec2(238/ptm_ratio, 167/ptm_ratio)  ,  new b2Vec2(295/ptm_ratio, 156/ptm_ratio)  ,  new b2Vec2(291/ptm_ratio, 164/ptm_ratio)  ] ,
                                                [   new b2Vec2(211/ptm_ratio, 100/ptm_ratio)  ,  new b2Vec2(213/ptm_ratio, 103/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(181/ptm_ratio, 77/ptm_ratio)  ,  new b2Vec2(185/ptm_ratio, 79/ptm_ratio)  ,  new b2Vec2(202/ptm_ratio, 91/ptm_ratio)  ,  new b2Vec2(207/ptm_ratio, 95/ptm_ratio)  ] ,
                                                [   new b2Vec2(202/ptm_ratio, 91/ptm_ratio)  ,  new b2Vec2(185/ptm_ratio, 79/ptm_ratio)  ,  new b2Vec2(194/ptm_ratio, 84/ptm_ratio)  ] ,
                                                [   new b2Vec2(163/ptm_ratio, 74/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(154/ptm_ratio, 71/ptm_ratio)  ] ,
                                                [   new b2Vec2(181/ptm_ratio, 77/ptm_ratio)  ,  new b2Vec2(163/ptm_ratio, 74/ptm_ratio)  ,  new b2Vec2(175/ptm_ratio, 75/ptm_ratio)  ] ,
                                                [   new b2Vec2(82/ptm_ratio, 188/ptm_ratio)  ,  new b2Vec2(112/ptm_ratio, 187/ptm_ratio)  ,  new b2Vec2(84/ptm_ratio, 190/ptm_ratio)  ] ,
                                                [   new b2Vec2(112/ptm_ratio, 187/ptm_ratio)  ,  new b2Vec2(127/ptm_ratio, 183/ptm_ratio)  ,  new b2Vec2(118/ptm_ratio, 186/ptm_ratio)  ] ,
                                                [   new b2Vec2(48/ptm_ratio, 120/ptm_ratio)  ,  new b2Vec2(138/ptm_ratio, 68/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(41/ptm_ratio, 132/ptm_ratio)  ,  new b2Vec2(44/ptm_ratio, 126/ptm_ratio)  ] ,
                                                [   new b2Vec2(223/ptm_ratio, 123/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(217/ptm_ratio, 110/ptm_ratio)  ,  new b2Vec2(222/ptm_ratio, 120/ptm_ratio)  ] ,
                                                [   new b2Vec2(39/ptm_ratio, 135/ptm_ratio)  ,  new b2Vec2(41/ptm_ratio, 132/ptm_ratio)  ,  new b2Vec2(208/ptm_ratio, 162/ptm_ratio)  ,  new b2Vec2(181/ptm_ratio, 162/ptm_ratio)  ] ,
                                                [   new b2Vec2(41/ptm_ratio, 132/ptm_ratio)  ,  new b2Vec2(139/ptm_ratio, 70/ptm_ratio)  ,  new b2Vec2(229/ptm_ratio, 136/ptm_ratio)  ,  new b2Vec2(238/ptm_ratio, 163/ptm_ratio)  ,  new b2Vec2(208/ptm_ratio, 162/ptm_ratio)  ] ,
                                                [   new b2Vec2(235/ptm_ratio, 138/ptm_ratio)  ,  new b2Vec2(238/ptm_ratio, 163/ptm_ratio)  ,  new b2Vec2(229/ptm_ratio, 136/ptm_ratio)  ] ,
                                                [   new b2Vec2(295/ptm_ratio, 156/ptm_ratio)  ,  new b2Vec2(245/ptm_ratio, 169/ptm_ratio)  ,  new b2Vec2(238/ptm_ratio, 167/ptm_ratio)  ]
											]

										]

									];

		}
	}
}
